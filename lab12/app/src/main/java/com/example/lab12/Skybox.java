package com.example.lab12;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Skybox {
	private static final int POSITON_COMPONENT_COUNT = 3;
	private final FloatBuffer vertexBuffer;
	private final float[] skyBoxCoords;
	
	public Skybox(){
		skyBoxCoords = new float[]{
				1,  1,  1,
				1, -1,  1,
				-1,  1,  1,
				-1,  1,  1,
				1, -1,  1,
				-1, -1,  1,

				-1,  1, -1,
				-1, -1, -1,
				1,  1, -1,
				1,  1, -1,
				-1, -1, -1,
				1, -1, -1,

				-1,  1,  1,
				-1, -1,  1,
				-1,  1, -1,
				-1,  1, -1,
				-1, -1,  1,
				-1, -1, -1,

				1,  1, -1,
				1, -1, -1,
				1,  1,  1,
				1,  1,  1,
				1, -1, -1,
				1, -1,  1,

				1,  1, -1,
				1,  1,  1,
				-1,  1, -1,
				-1,  1, -1,
				1,  1,  1,
				-1,  1,  1,

				-1, -1, -1,
				-1, -1,  1,
				1, -1, -1,
				1, -1, -1,
				-1, -1,  1,
				1, -1,  1
		};
		

		vertexBuffer = ByteBuffer.allocateDirect(skyBoxCoords.length*4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(skyBoxCoords);

	}
	
	public void bindData(SkyboxShaderProgram skyboxProgram){

		int attributeLocation = skyboxProgram.getPositionAttributeLocation();
		vertexBuffer.position(0);
		glVertexAttribPointer(attributeLocation,POSITON_COMPONENT_COUNT, GL_FLOAT,
				false, 0, vertexBuffer);
		glEnableVertexAttribArray(attributeLocation);
		vertexBuffer.position(0);
	}
	
	public void draw(){
		glDrawArrays(GL_TRIANGLES, 0, 36*3); //3 vertices for triangle
	}
}
