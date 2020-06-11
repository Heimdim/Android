package com.example.lab12;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Skybox {
	private static final int POSITON_COMPONENT_COUNT = 3;
	private final FloatBuffer vertexBuffer;
	private final float[] skyBoxCoords;
	private final ByteBuffer indexArray;
	
	public Skybox(){
		skyBoxCoords = new float[]{
			-1,  1,  1,//0
			 1,  1,  1,//1
			-1, -1,  1,//2
			 1, -1,  1,//3
			-1,  1, -1,//4
			 1,  1, -1,//5
			-1, -1, -1,//6
			 1, -1, -1 //7
		};

		indexArray = ByteBuffer.allocateDirect(6*6)
				.put(new byte[]{
					//front
					1,3,0,
					0,3,2,
					
					//back
					4,6,5,
					5,6,7,
					
					//left
					0,2,4,
					4,2,6,
					
					//right
					5,7,1,
					1,7,3,
					
					//top
					5,1,4,
					4,1,0,
					
					//bottom
					6,2,7,
					7,2,3
				});

		vertexBuffer = ByteBuffer.allocateDirect(skyBoxCoords.length*4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(skyBoxCoords);

		indexArray.position(0);
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
		glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indexArray);
	}
}
