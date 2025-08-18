package dev.anvilcraft.addon.delta.mixin;

import dev.anvilcraft.addon.delta.util.ISimplePowerGridExtension;
import dev.anvilcraft.addon.delta.util.PowerTransmitterLinesUtil;
import dev.dubhe.anvilcraft.api.power.PowerComponentInfo;
import dev.dubhe.anvilcraft.api.power.PowerComponentType;
import dev.dubhe.anvilcraft.api.power.SimplePowerGrid;
import dev.dubhe.anvilcraft.client.renderer.Line;
import net.minecraft.world.phys.Vec3;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(SimplePowerGrid.class)
public class SimplePowerGridMixin implements ISimplePowerGridExtension {
    @Shadow
    @Final
    private List<PowerComponentInfo> powerComponentInfoList;
    @Unique
    private Set<Line> delta$powerTransmitterLines = Set.of();

    @Override
    public Set<Line> delta$getPowerTransmitterLines() {
        return this.delta$powerTransmitterLines;
    }

    @Override
    public void delta$createPowerTransmitterLines() {
        if (!this.delta$powerTransmitterLines.isEmpty()) return;
        List<Map.Entry<Vec3, Integer>> shapes = this.powerComponentInfoList.stream()
            .filter(it -> it.type() == PowerComponentType.TRANSMITTER)
            .map(it -> Map.entry(it.pos().getCenter(), it.range()))
            .toList();
        if (shapes.size() <= 2) {
            if (shapes.size() == 2) {
                this.delta$powerTransmitterLines = Set.of(
                    new Line(
                        shapes.get(0).getKey(),
                        shapes.get(1).getKey(),
                        (float) shapes.get(0).getKey().distanceTo(shapes.get(1).getKey())
                    )
                );
            }
            return;
        }
        Map<Vec3, Integer> map = Map.ofEntries(shapes.<Map.Entry<Vec3, Integer>>toArray(Map.Entry[]::new));
        List<Map.Entry<Coordinate, Vec3>> point = shapes.stream()
            .map(it -> {
                double offset = (it.getKey().y - 1024) / 2048;
                double x = it.getKey().x + offset;
                double z = it.getKey().z + offset;
                return Map.entry(new Coordinate(x, z), it.getKey());
            })
            .toList();
        Map<Coordinate, Vec3> map1 = Map.ofEntries(point.<Map.Entry<Coordinate, Vec3>>toArray(Map.Entry[]::new));
        List<Coordinate> points = map1.keySet().stream().toList();
        DelaunayTriangulationBuilder triangulator = new DelaunayTriangulationBuilder();
        triangulator.setSites(points);
        Geometry triangles = triangulator.getTriangles(new GeometryFactory());
        Set<Line> lines = new HashSet<>();
        for (int i = 0; i < triangles.getNumGeometries(); i++) {
            Geometry triangle = triangles.getGeometryN(i);
            Coordinate[] coordinates = triangle.getCoordinates();
            if (coordinates.length <= 1) continue;
            for (int j = 1; j < coordinates.length; j++) {
                Coordinate p1 = coordinates[j - 1];
                Coordinate p2 = coordinates[j];
                Vec3 vec3 = map1.get(p1);
                Vec3 vec4 = map1.get(p2);
                double v = vec3.distanceTo(vec4);
                int i1 = map.getOrDefault(vec3, 0);
                int i2 = map.getOrDefault(vec4, 0);
                if (!PowerTransmitterLinesUtil.isOverlap(vec3, i1, vec4, i2)) continue;
                lines.add(new Line(
                    vec3,
                    vec4,
                    (float) v
                ));
            }
        }
        this.delta$powerTransmitterLines = lines;
    }
}
