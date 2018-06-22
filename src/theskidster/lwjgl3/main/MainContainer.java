package theskidster.lwjgl3.main;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author J Hoffman
 * Created: Jun 20, 2018
 */

public class MainContainer implements Runnable {

    private final int WIDTH = 640;
    private final int HEIGHT = 448;
    private int vao;
    private int vbo;
    private int mode;
    
    private long context;
    
    private float[] bufferData = {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
    };
    
    private GLFWVidMode vm;
    
    /**
     * Establishes GLFW window and context
     */
    public MainContainer() {
        if(!glfwInit()) throw new IllegalStateException("could not initialize glfw.");
        
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        
        context = glfwCreateWindow(WIDTH, HEIGHT, "Simple triangle example.", NULL, NULL);
        vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(context, (vm.width() - WIDTH) / 2, (vm.height() - HEIGHT) / 2);
        
        glfwSetKeyCallback(context, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)  glfwSetWindowShouldClose(context, true);
        });
        
        glfwMakeContextCurrent(context);
        glfwSwapInterval(1);
        glfwShowWindow(context);
    }
    
    /**
     * Binds vertex objects, includes simple rendering loop
     */
    @Override
    public void run() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, bufferData, GL_STATIC_DRAW);
        
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        
        while(!glfwWindowShouldClose(context)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            glDrawArrays(GL_TRIANGLES, 0, 3);
            
            glfwSwapBuffers(context);
            glfwPollEvents();
        }
        glfwTerminate();
    }
    
}