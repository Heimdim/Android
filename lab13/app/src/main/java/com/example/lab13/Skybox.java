package com.example.lab13;


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
				1,  1,  1,  1.0f,  0.0f, 0.0f,//1
				1, -1,  1,   1.0f,  0.0f, 0.0f,//3
				-1,  1,  1,  1.0f,  0.0f, 0.0f,//0
				-1,  1,  1,  1.0f,  0.0f, 0.0f,//0
				1, -1,  1,   1.0f,  0.0f, 0.0f,//3
				-1, -1,  1,   1.0f,  0.0f, 0.0f,//2

				-1,  1, -1, 1.0f,0.0f,  0.0f,//4
				-1, -1, -1, 1.0f,0.0f,  0.0f,//6
				1,  1, -1, 1.0f, 0.0f,  0.0f,//5
				1,  1, -1, 1.0f,  0.0f,  0.0f,//5
				-1, -1, -1, 1.0f, 0.0f,  0.0f,//6
				1, -1, -1, 1.0f,  0.0f,  0.0f, //7

				-1,  1,  1, 1.0f,  0.0f,  0.0f,//0
				-1, -1,  1, 1.0f,  0.0f,  0.0f,//2
				-1,  1, -1, 1.0f,  0.0f,  0.0f,//4
				-1,  1, -1, 1.0f,  0.0f,  0.0f,//4
				-1, -1,  1, 1.0f,  0.0f,  0.0f,//2
				-1, -1, -1, 1.0f,  0.0f,  0.0f,//6

				1,  1, -1, 1.0f,  0.0f,  0.0f,//5
				1, -1, -1,1.0f,  0.0f,  0.0f,//7
				1,  1,  1,1.0f,  0.0f,  0.0f,//1
				1,  1,  1,1.0f,  0.0f,  0.0f,//1
				1, -1, -1,1.0f,  0.0f,  0.0f,//7
				1, -1,  1,1.0f,  0.0f,  0.0f,//3

				1,  1, -1, 0.0f, 1.0f,  0.0f,//5
				1,  1,  1, 0.0f, 1.0f,  0.0f,//1
				-1,  1, -1, 0.0f, 1.0f,  0.0f,//4
				-1,  1, -1, 0.0f, 1.0f,  0.0f,//4
				1,  1,  1, 0.0f, 1.0f,  0.0f,//1
				-1,  1,  1, 0.0f, 1.0f,  0.0f,//0

				-1, -1, -1, 0.0f,  -1.0f,  0.0f,//6
				-1, -1,  1, 0.0f,  -1.0f,  0.0f,//2
				1, -1, -1, 0.0f,  -1.0f,  0.0f,//7
				1, -1, -1, 0.0f,  -1.0f,  0.0f,//7
				-1, -1,  1, 0.0f, -1.0f,  0.0f,//2
				1, -1,  1, 0.0f, -1.0f,  0.0f//3
		};
		

		vertexBuffer = ByteBuffer.allocateDirect(skyBoxCoords.length*4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(skyBoxCoords);

	}
	
	public void bindData(SkyboxShaderProgram skyboxProgram){

		int attributeLocation = skyboxProgram.getPositionAttributeLocation();
		int norm = skyboxProgram.mNormal;
		vertexBuffer.position(0);
		glVertexAttribPointer(attributeLocation,POSITON_COMPONENT_COUNT, GL_FLOAT,
				false, 24, vertexBuffer);
		glEnableVertexAttribArray(attributeLocation);

		vertexBuffer.position(POSITON_COMPONENT_COUNT);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false,24,vertexBuffer);
		glEnableVertexAttribArray(norm);


	}
	
	public void draw(){
		glDrawArrays(GL_TRIANGLES, 0, 35*3); //3 vertices for triangle

	}
}
