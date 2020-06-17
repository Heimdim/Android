package com.example.lab12;

import static android.opengl.GLES20.*;

public class SkyboxShaderProgram {

	private final int uMatrixLocation;
	private final int uTextureUnitLocation;
	private final int aPositionLocation;

	final String vertexShader =
			"uniform mat4 u_Matrix;" +
					"attribute vec3 a_Position;" +
					"varying vec3 v_Position;" +
					"void main()" +
					"{"+
					"v_Position = a_Position;" +
					"v_Position.z = -v_Position.z;" +
					"gl_Position = u_Matrix*vec4(a_Position, 1.0);" +
					"gl_Position = gl_Position.xyww;" +
					"}";
	final String fragmentShader =
					"precision mediump float;" +
					"uniform samplerCube u_TextureUnit;" +
					"varying vec3 v_Position;" +
					"void main()" +
					"{" +
					"gl_FragColor = textureCube(u_TextureUnit, v_Position);" +
					"}";


	public SkyboxShaderProgram() {
		int vertexShaderId =compileShader(GL_VERTEX_SHADER, vertexShader);
		int fragmentShaderId = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

		final int programObjectId = glCreateProgram();
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		glLinkProgram(programObjectId);

		uMatrixLocation = glGetUniformLocation(programObjectId, "u_Matrix");
		uTextureUnitLocation = glGetUniformLocation(programObjectId, "u_TextureUnit");
		aPositionLocation = glGetAttribLocation(programObjectId, "a_Position");

		glUseProgram(programObjectId);
	}

	private static int compileShader(int type, String shaderCode) {
		final int shaderObjectId = glCreateShader(type);
		glShaderSource(shaderObjectId, shaderCode);
		glCompileShader(shaderObjectId);
		return shaderObjectId;
	}


	public void setUniforms(float[] matrix, int textureId){
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
		glUniform1i(uTextureUnitLocation, 0);
	}

	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}

}
