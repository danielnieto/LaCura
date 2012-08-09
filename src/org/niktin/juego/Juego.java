package org.niktin.juego;

import org.niktin.utilidades.Punto;
import org.niktin.utilidades.Utilidades;
import org.lwjgl.openal.AL;
import org.niktin.entrada.Raton;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.niktin.recursos.Fuente;
import org.niktin.recursos.Vacio;
import java.lang.Object;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.niktin.recursos.Recurso;
import static org.lwjgl.opengl.GL11.*;


public class Juego{

private boolean malla = false;
public int contador=0;
private Image buffer;
private Graphics2D gb;
private static int anchura,altura,contadorFPS;
private Color colorDeFondo = Color.BLACK;
public static ArrayList<Recurso> recursos;
private int cuadrosPorSegundoReales;
private int cuadrosPorSegundo;
private BufferStrategy estrategia;
private boolean sincronizacion=false;
private boolean sincronizacionVertical=true;
private ArrayList<Fuente> fuentes;
private static float posicionVistaX=0, posicionVistaY=0;
private long ultimoFPS;
private int fps, fpsARegresar;



public Juego(int anchura,int altura, int fps){
    Juego.anchura = anchura;
    Juego.altura = altura;
    cuadrosPorSegundo = fps;
    ultimoFPS = Utilidades.obtenerTiempo();


    recursos = new ArrayList();
    fuentes = new ArrayList();



    //calentamiento
    Utilidades.obtenerTiempo();
        try {
            inicializar();
        } catch (LWJGLException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
      
}

public void actualizar(){

        Display.update();
      
        glLoadIdentity();

        glClearColor(colorDeFondo.getRed(), colorDeFondo.getGreen(), colorDeFondo.getBlue(),0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glColor3f(1.0f, 1.0f, 1.0f);

        glOrtho(0.0, anchura, 0.0, altura, 0, 1);

       

        if(sincronizacion){
        Display.sync(cuadrosPorSegundo);
        }


        //Display.setVSyncEnabled(sincronizacionVertical);


       for(Recurso item:recursos){
            dibujarRecurso(item);
            if(item.obtenerArrastre()){
                item.asignarX(item.obtenerX()+Raton.obtenerDX());
                item.asignarY(item.obtenerY()+Raton.obtenerDY());
            }
        }

        for(Fuente item:fuentes){
            item.render();
        }

        if(Display.isCloseRequested()){

            Display.destroy();

            AL.destroy();
            System.exit(0);
        }
        


}

public void asignarMallaVisible(boolean visible){
    malla=visible;
}

public boolean obtenerMallaVisible(){
return malla;
}

public void asignarColorDeFondo(Color color){
    colorDeFondo = color;
}

public Color obtenerColorDeFondo(){
    return colorDeFondo;
}

public static void agregarRecurso(Recurso recurso,double posicionX,double posicionY){

    if(!recursos.contains(recurso)){
    recurso.asignarX(posicionX);
    recurso.asignarY(posicionY);

    recursos.add(recurso);
    }
}

public void borrarRecursos(){
    recursos.clear();
}

public void removerRecurso(Recurso recursoARemover){
     if(recursos.contains(recursoARemover)){
        recursos.remove(recursoARemover);
    }
}

public int obtenerFPS(){
    return cuadrosPorSegundo;
}

public static float obtenerPosicionVistaX(){
    return posicionVistaX;
}

public static float obtenerPosicionVistaY(){
    return posicionVistaY;
}

public static void asignarPosicionVistaX(float nuevaX){
    posicionVistaX = nuevaX;
}

public static void asignarPosicionVistaY(float nuevaY){
    posicionVistaY = nuevaY;
}

    private void dibujarRecurso(Recurso recurso) {

    if(recurso instanceof Vacio==false){

        Punto p1=recurso.obtenerP1(),p2=recurso.obtenerP2(),p3=recurso.obtenerP3(),p4=recurso.obtenerP4();
        glPushMatrix();
        glColor3f(recurso.obtenerMascaraColorRojo(),recurso.obtenerMascaraColorVerde(),recurso.obtenerMascaraColorAzul());
        
       

       recurso.obtenerImagen().vincular();
        
        //rotacion
        if(recurso.esEstatico()==false){
            glTranslatef(posicionVistaX, posicionVistaY, 0);
        }
       /* glTranslatef((float)recurso.obtenerPivoteXParaRotacion(), (float)recurso.obtenerPivoteYParaRotacion(), 0f);
        System.out.println(recurso.obtenerPivoteXParaRotacion());
        glRotatef((float)recurso.obtenerAnguloDeRotacion(), 0f, 0f, 1f);
        glTranslatef((float)-recurso.obtenerPivoteXParaRotacion(), (float)-recurso.obtenerPivoteYParaRotacion(), 0f);*/
        //escalacion
      //  glTranslatef(recurso.obtenerPivoteXParaEscalacion(), recurso.obtenerPivoteYParaEscalacion(), 0);
       // glScalef(recurso.obtenerFactorXDeEscalacion(),recurso.obtenerFactorYDeEscalacion(), 0f);
       // glTranslatef(-recurso.obtenerPivoteXParaEscalacion(), -recurso.obtenerPivoteYParaEscalacion(), 0);
   


       glBegin(GL_QUADS);

        glTexCoord2d(recurso.mapeoFotograma1a,recurso.mapeoFotograma1b);//01
        glVertex2d(p1.x,p1.y);
        glTexCoord2d(recurso.mapeoFotograma2a,recurso.mapeoFotograma2b);//11
        glVertex2d(p2.x,p2.y);
        glTexCoord2d(recurso.mapeoFotograma3a,recurso.mapeoFotograma3b);//10
        glVertex2d(p3.x,p3.y);
        glTexCoord2d(recurso.mapeoFotograma4a,recurso.mapeoFotograma4b);//00
        glVertex2d(p4.x,p4.y);
    
        glEnd();



        glPopMatrix();

}
    
       
    }

    private void inicializar() throws LWJGLException {
    
    Display.setDisplayMode(new DisplayMode(anchura,altura));
    
    Display.create();

    glViewport(0,0,anchura,altura);
    glMatrixMode(GL_MODELVIEW);
  
   
    glEnable(GL_TEXTURE_2D);///////////////////ACTIVAAAR!
     	// enable alpha blending

    
 
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  //   glOrtho(0.0, anchura, 0.0, altura, 0, 1);

    
 
    }

    public static int obtenerAltura(){

        return altura;
    }

    public static int obtenerAnchura(){
        return anchura;
    }

public void asignarSincronizacion(boolean sincronizacion){
    this.sincronizacion = sincronizacion;
}

public void asignarSincronizacionVertical(boolean sincronizacionVertical){
    this.sincronizacionVertical = sincronizacionVertical;
}



public void dibujarTexto(Fuente fuente, String texto, int x, int y){

    if(fuentes.contains(fuente)){
        fuentes.remove(fuente);
    }

        fuente.asignarX(x);
        fuente.asignarY(y);
        fuente.asignarTexto(texto);
        fuentes.add(fuente);

}

public void borrarTexto(Fuente fuente){
    if(fuentes.contains(fuente)){
        fuentes.remove(fuente);
    }
}

public void asignarTitulo(String titulo){

    Display.setTitle(titulo);
}

public int obtenerCuadrosPorSegundo() {
		if (Utilidades.obtenerTiempo() - ultimoFPS > 1000) {
			fpsARegresar = fps;
                        fps = 0;
			ultimoFPS += 1000;

		}
		fps++;

                return fpsARegresar;
	}




}
