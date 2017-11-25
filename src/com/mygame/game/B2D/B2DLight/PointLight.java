package com.mygame.game.B2D.B2DLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Light shaped as a circle with given radius
 * 
 * <p>Extends {@link PositionalLight}
 * 
 * @author kalle_h
 */
public class PointLight extends PositionalLight {

	/**
	 * Creates light shaped as a circle with default radius (15f), color and
	 * position (0f, 0f)
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 */
	public PointLight(RayHandler rayHandler, int rays) {
		this(rayHandler, rays, Light.DefaultColor, 15f, 0f, 0f);
	}
	
	/**
	 * Creates light shaped as a circle with given radius
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 * @param color
	 *            color, set to {@code null} to use the default color
	 * @param distance
	 *            distance of light
	 * @param x
	 *            horizontal position in world coordinates
	 * @param y
	 *            vertical position in world coordinates
	 */
	public PointLight(RayHandler rayHandler, int rays, Color color,
			float distance, float x, float y) {
		super(rayHandler, rays, color, distance, x, y, 0f);
	}
	
	@Override
	public void update () {
		updateBody();
		if (dirty) setEndPoints();
		
		if (cull()) return;
		if (staticLight && !dirty) return;
		
		dirty = false;
		updateMesh();
		
		if (rayHandler.pseudo3d && height != 0f) {
			prepeareFixtureData();
		}
	}
	
	@Override
	void dynamicShadowRender () {
		if (height == 0f) return;
		
		updateDynamicShadowMeshes();
		for (Mesh m : dynamicShadowMeshes) {
			m.render(rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP);
		}
	}
	
	
	protected void updateDynamicShadowMeshes() {
		for (Mesh mesh : dynamicShadowMeshes) {
			mesh.dispose();
		}
		dynamicShadowMeshes.clear();
		
		if (dynamicSegments == null) {
			dynamicSegments = new float[vertexNum * 16];
		}
		
		float colBits = rayHandler.ambientLight.toFloatBits();
		for (Fixture fixture : affectedFixtures) {
			LightData data = (LightData) fixture.getUserData();
			Shape fixtureShape = fixture.getShape();
			if (fixtureShape instanceof PolygonShape) {
				Mesh mesh = new Mesh(
						VertexDataType.VertexArray, staticLight, 2 * vertexNum, 0,
						new VertexAttribute(Usage.Position, 2, "vertex_positions"),
						new VertexAttribute(Usage.ColorPacked, 4, "quad_colors"),
						new VertexAttribute(Usage.Generic, 1, "s"));
				PolygonShape shape = (PolygonShape)fixtureShape;
				int size = 0;
				float l;
				float dst = fixture.getBody().getWorldCenter().dst(start);
				float f = 1f / data.shadowsDropped;
				for (int n = 0; n < shape.getVertexCount()-2; n++) {
					shape.getVertex(n, tmpVec);
					tmpVec.set(fixture.getBody().getWorldPoint(tmpVec));
					
					dynamicSegments[size++] = tmpVec.x;
					dynamicSegments[size++] = tmpVec.y;
					dynamicSegments[size++] = colBits;
					dynamicSegments[size++] = f;
					
					if (height > data.height) {
						l = dst * data.height / (height - data.height);
						if (l > distance - dst) l = distance - dst;
					} else {
						l = distance - dst;
					}
					
					tmpEnd.set(tmpVec).sub(start).limit(l).add(tmpVec);
					dynamicSegments[size++] = tmpEnd.x;
					dynamicSegments[size++] = tmpEnd.y;
					dynamicSegments[size++] = colBits;
					dynamicSegments[size++] = f;
				}
				mesh.setVertices(dynamicSegments, 0, size);
				dynamicShadowMeshes.add(mesh);
			}
		}
	}
	
	/**
	 * Sets light distance
	 * 
	 * <p>MIN value capped to 0.1f meter
	 * <p>Actual recalculations will be done only on {@link #update()} call
	 */
	@Override
	public void setDistance(float dist) {
		dist *= RayHandler.gammaCorrectionParameter;
		this.distance = dist < 0.01f ? 0.01f : dist;
		dirty = true;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	/** Updates light basing on it's distance and rayNum **/
	void setEndPoints() {
		float angleNum = 360f / (rayNum - 1);
		for (int i = 0; i < rayNum; i++) {
			final float angle = angleNum * i;
			sin[i] = MathUtils.sinDeg(angle);
			cos[i] = MathUtils.cosDeg(angle);
			endX[i] = distance * cos[i];
			endY[i] = distance * sin[i];
		}
	}
	
	/** Not applicable for this light type **/
	@Deprecated
	@Override
	public void setDirection(float directionDegree) {
	}

}

