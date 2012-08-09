/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.niktin.entrada.Raton;
import org.niktin.entrada.Teclado;
import org.niktin.juego.Juego;
import org.niktin.recursos.Fuente;
import org.niktin.recursos.Recurso;
import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;

/**
 *
 * @author Daniel
 */
public class Bichos {
    Juego juego;
    private long ultimoFPS;
    private int fps;
    Recurso escenario, colision, colision2;
    Bicho1 bicho1;
    Textura bicho1tex;
    Textura bicho2tex;
    Textura defensa1tex;
    Textura bicho3tex;
    Textura defensa3tex;
    public static Textura proyectil1tex;
    private int ultimaPosicionRaton;
    private long siguienteTick;
    private int ticks=30;
    private boolean arrastrandoPantalla=false;
    private float ultimaPosicionPantalla;
    public static ArrayList<Ser> buenos,malos;
    public Defensa1 defensa1;
    public Bicho2 bicho2;
    public Defensa2 defensa2;
    public Bicho3 bicho3;
    public Defensa3 defensa3;
    private ArrayList<Ser> seresAEliminar = new ArrayList();
    private ArrayList<Proyectil> proyectilesAEliminar = new ArrayList();
    public static ArrayList<Proyectil> proyectiles = new ArrayList();
    private Textura defensa2tex;
    public static Textura proyectil2tex;
    public int puntos=15;
    public long ultimoPuntos;
    private Recurso barra;
    private Recurso boton1,boton2, boton3;
    private Textura texb1n, texb2n, texb3n, texb1d, texb2d, texb3d;
    private Recurso cargaBarra;
    private DefensaMayor adn;
    private BichoMayor chiefVirus;
    private double porcentajeBarraAnterior;
    private int porcentajeBarra=0;
    private long ultimoEnemigo;
    private Fuente fuente1,fuente2,fuente3;
    private Recurso barraSuperior;
    private int sentidoMovimientoTextoGana=0;
    private int movimientoTextoGana=0;
    private Fuente ftGanador;
    private Recurso fondo;
    private Recurso title;





    private void moverEscenario() {

        

        if(escenario.ratonSobreObjeto()&&Raton.botonPresionado(Raton.BOTON_IZQUIERDO)&&!arrastrandoPantalla){
            arrastrandoPantalla = true;
            ultimaPosicionRaton = Raton.obtenerX();
            ultimaPosicionPantalla = juego.obtenerPosicionVistaX();
        }

        if(escenario.ratonSobreObjeto()&&Raton.botonPresionado(Raton.BOTON_IZQUIERDO)&&arrastrandoPantalla){
            int posicionActualRaton = Raton.obtenerX();
            float delta = posicionActualRaton-ultimaPosicionRaton;
            
            float posicionFinalPantalla = ultimaPosicionPantalla+delta;

            if(posicionFinalPantalla>0){
                posicionFinalPantalla =  0;  
            }
            if(posicionFinalPantalla<-1200){
                posicionFinalPantalla =  -1200;
            }

            juego.asignarPosicionVistaX(posicionFinalPantalla);       
            

        }else{
            arrastrandoPantalla=false;
            ultimaPosicionPantalla = juego.obtenerPosicionVistaX();

        }
    }

    private void moverElementos() {
       

   


       /*if(bicho1.colisionaConAlguno()&&(bicho1.obtenerFotogramaActual()==30||bicho1.obtenerFotogramaActual()==1||(bicho1.obtenerFotogramaActual()>=30&&bicho1.obtenerFotogramaActual()<=50))){
            bicho1.atacar(30,50,.02f);
            
        }else if(!bicho1.colisionaConAlguno()&&bicho1.estado==bicho1.estado.atacando){
            bicho1.atacar(30,50,.02f);
            if(bicho1.obtenerFotogramaActual()==31||bicho1.obtenerFotogramaActual()==50){
                //bicho1.desplazar(1,30,.02f);
                bicho1.estado=bicho1.estado.desplazandose;
                
            }
        } else {

            bicho1.desplazar(1,30,.02f);
        }
  */

        /*if(!bicho1.colisionaConAlguno()){

            bicho1.desplazar(1, 30,.02f);
        }*/

        proyectilesAEliminar.clear();
        for(Proyectil proyectil:proyectiles){
            proyectil.actualizarEstado();
            
            if(proyectil.colisionaConAlgunEnemigo()){
                Ser enemigo = proyectil.conQueEnemigoColisiona();
                if(enemigo!=null){
                    enemigo.puntosVida-=proyectil.puntosAtaque+Utilidades.aleatorio(proyectil.puntosAtaque*proyectil.factorRandomAtaque,-proyectil.puntosAtaque*proyectil.factorRandomAtaque);
                    proyectilesAEliminar.add(proyectil);
                    juego.removerRecurso(proyectil);
                }
            }

        }
        
        for(Proyectil proyectil: proyectilesAEliminar){
        
            proyectiles.remove(proyectil);
        }


      
      for(Ser defensa: buenos){
          defensa.actualizarEstado();
          if(defensa.puntosVida<=0){
            seresAEliminar.add(defensa);
            juego.removerRecurso(defensa);
            
          }
      }

      for(Ser bicho: malos){
           bicho.actualizarEstado();
          if(bicho.puntosVida<=0){
            seresAEliminar.add(bicho);
            juego.removerRecurso(bicho);

          }
      }

      for(Ser muerto: seresAEliminar){
            switch(muerto.bando){
                case bueno:
                        buenos.remove(muerto);
                    break;
                case malo:
                        malos.remove(muerto);
                    break;
            }
      }

      seresAEliminar.clear();     
        
        
    }

    private void escuchaTeclado() {
        if(Teclado.teclaPresionada(Teclado.TECLA_1)&&Teclado.obtenerIntervalo()>100 ){
            Defensa1 defensa = new Defensa1();
            buenos.add(defensa);
            juego.agregarRecurso(defensa, 0,200);
        }

        if(Teclado.teclaPresionada(Teclado.TECLA_2)&&Teclado.obtenerIntervalo()>100 ){
            Defensa2 defensa = new Defensa2();
            buenos.add(defensa);
            juego.agregarRecurso(defensa, 0,200);
        }

         if(Teclado.teclaPresionada(Teclado.TECLA_3)&&Teclado.obtenerIntervalo()>100 ){
            Defensa3 defensa = new Defensa3();
            buenos.add(defensa);
            juego.agregarRecurso(defensa, 0,200);
        }

        if(Teclado.teclaPresionada(Teclado.TECLA_TECLADO_NUMERICO_1)&&Teclado.obtenerIntervalo()>100 ){
            Bicho1 bicho = new Bicho1();
            malos.add(bicho);
            juego.agregarRecurso(bicho, 1000,200);
        }

        if(Teclado.teclaPresionada(Teclado.TECLA_TECLADO_NUMERICO_2)&&Teclado.obtenerIntervalo()>100 ){
            Bicho2 bicho = new Bicho2();
            malos.add(bicho);
            juego.agregarRecurso(bicho, 1000,200);
        }

        if(Teclado.teclaPresionada(Teclado.TECLA_TECLADO_NUMERICO_3)&&Teclado.obtenerIntervalo()>100 ){
            Bicho3 bicho = new Bicho3();
            malos.add(bicho);
            juego.agregarRecurso(bicho, 1000,200);
        }

    }

    private void actualizarPuntosYEnemigos() {

        if(puntos>=15){

        boton1.asignarImagen(texb1n);

        if(boton1.ratonSobreObjeto()){

            boton1.aplicarMascaraDeColor(boton1.obtenerMascaraColorRojo()-.1f, boton1.obtenerMascaraColorVerde()+.1f, boton1.obtenerMascaraColorAzul()-.1f);
        }else{
            boton1.aplicarMascaraDeColor(boton1.obtenerMascaraColorRojo()+.1f, boton1.obtenerMascaraColorVerde()+.1f, boton1.obtenerMascaraColorAzul()+.1f);
        }


         if(boton2.ratonSobreObjeto()){

            boton2.aplicarMascaraDeColor(boton2.obtenerMascaraColorRojo()-.1f, boton2.obtenerMascaraColorVerde()+.1f, boton2.obtenerMascaraColorAzul()-.1f);
        }else{
            boton2.aplicarMascaraDeColor(boton2.obtenerMascaraColorRojo()+.1f, boton2.obtenerMascaraColorVerde()+.1f, boton2.obtenerMascaraColorAzul()+.1f);
        }


         if(boton3.ratonSobreObjeto()){

            boton3.aplicarMascaraDeColor(boton3.obtenerMascaraColorRojo()-.1f, boton3.obtenerMascaraColorVerde()+.1f, boton3.obtenerMascaraColorAzul()-.1f);
        }else{
            boton3.aplicarMascaraDeColor(boton3.obtenerMascaraColorRojo()+.1f, boton3.obtenerMascaraColorVerde()+.1f, boton3.obtenerMascaraColorAzul()+.1f);
        }
        
        if(boton1.ratonSobreObjeto()&&Raton.botonPresionado(Raton.BOTON_IZQUIERDO)&&Raton.obtenerIntervalo()>50){
            Defensa1 defensa1 = new Defensa1();
            buenos.add(defensa1);
            juego.agregarRecurso(defensa1, 0-defensa1.obtenerAnchura(), 200);
            puntos-=15;
            
        }

        }else{
        boton1.asignarImagen(texb1d);
        boton1.aplicarMascaraDeColor(boton1.obtenerMascaraColorRojo()+.1f, boton1.obtenerMascaraColorVerde()+.1f, boton1.obtenerMascaraColorAzul()+.1f);
        }

        if(puntos>=30){

        boton2.asignarImagen(texb2n);

        if(boton2.ratonSobreObjeto()&&Raton.botonPresionado(Raton.BOTON_IZQUIERDO)&&Raton.obtenerIntervalo()>50){
            Defensa2 defensa2 = new Defensa2();
            buenos.add(defensa2);
            juego.agregarRecurso(defensa2, 0-defensa2.obtenerAnchura(), 200);
            puntos-=30;

        }

        }else{
        boton2.asignarImagen(texb2d);
        boton2.aplicarMascaraDeColor(boton2.obtenerMascaraColorRojo()+.1f, boton2.obtenerMascaraColorVerde()+.1f, boton2.obtenerMascaraColorAzul()+.1f);
        }

        if(puntos>=50){

        boton3.asignarImagen(texb3n);

        if(boton3.ratonSobreObjeto()&&Raton.botonPresionado(Raton.BOTON_IZQUIERDO)&&Raton.obtenerIntervalo()>50){
            Defensa3 defensa3 = new Defensa3();
            buenos.add(defensa3);
            juego.agregarRecurso(defensa3, 0-defensa3.obtenerAnchura(), 200);
            puntos-=50;

        }

        }else{
        boton3.asignarImagen(texb3d);
        boton3.aplicarMascaraDeColor(boton3.obtenerMascaraColorRojo()+.1f, boton3.obtenerMascaraColorVerde()+.1f, boton3.obtenerMascaraColorAzul()+.1f);
        }

        long actualPuntos = Utilidades.obtenerTiempo();

        if(actualPuntos-ultimoPuntos>200){
            puntos++;
            System.out.println(puntos);
            ultimoPuntos=actualPuntos;

        }


        juego.dibujarTexto(fuente1, ""+puntos, 65, 151);

        juego.dibujarTexto(fuente2, ""+(int)adn.puntosVida+"/1000", 10, 560);

        juego.dibujarTexto(fuente3, (int)chiefVirus.puntosVida+"/1000", 800-fuente3.obtenerAnchura((int)chiefVirus.puntosVida+"/3000")-10, 560);


        long actualEnemigo = Utilidades.obtenerTiempo();

        if(actualEnemigo>ultimoEnemigo){

            int bichoRandom = Utilidades.aleatorio(0, 3);

            switch(bichoRandom){
                case 1:
                    Bicho1 bicho = new Bicho1();
                    malos.add(bicho);
                    juego.agregarRecurso(bicho, 2000,200);
                    break;
                case 2:
                    Bicho2 bicho2 = new Bicho2();
                    malos.add(bicho2);
                    juego.agregarRecurso(bicho2, 2000,200);
                    break;
                case 3:
                    Bicho3 bicho3 = new Bicho3();
                    malos.add(bicho3);
                    juego.agregarRecurso(bicho3, 2000,200);
                    break;

            }
            
            

            ultimoEnemigo+=Utilidades.aleatorio(1000, 10000);


        }



       

        porcentajeBarraAnterior = porcentajeBarra;
        
        porcentajeBarra = (puntos*500)/700;
        
        cargaBarra.asignarAnchura(20);

 

        
        //cargaBarra.escalar(1.1, 1, cargaBarra.obtenerX(), 150);


    }

    private void muestraTitulo() {
        juego.borrarRecursos();
        juego.agregarRecurso(title, 0,0);

        if(Teclado.teclaPresionada(Teclado.TECLA_ESPACIO)&&Teclado.obtenerIntervalo()>50){
            inicializarJuego();
            estado = estado.jugando;

        }

    }

    private void muestraGanador(String ganador) {

         if(sentidoMovimientoTextoGana==0){
                    movimientoTextoGana++;
           if(movimientoTextoGana==40){
                sentidoMovimientoTextoGana=1;
           }
    }else if(sentidoMovimientoTextoGana==1){
                    movimientoTextoGana--;
           if(movimientoTextoGana==-40){
                sentidoMovimientoTextoGana=0;
           }
    }

    juego.dibujarTexto(ftGanador,ganador, Juego.obtenerAnchura()/2 - (ftGanador.obtenerAnchura(ganador)/2) + movimientoTextoGana , Juego.obtenerAltura()/2-ftGanador.obtenerAltura(ganador)/2);

     if(Teclado.teclaPresionada(Teclado.TECLA_ESPACIO)&&Teclado.obtenerIntervalo()>50){
         juego.borrarTexto(ftGanador);
            inicializarJuego();
            estado = estado.jugando;

        }


    }
    enum Estados{jugando, ganaCPU, ganaJugador, titulo};
    Estados estado = Estados.titulo;

    Bichos(){
        juego = new Juego(800,600,60);
        juego.asignarSincronizacion(false);
        juego.asignarSincronizacionVertical(false);
/*
        Thread timerAccuracyThread = new Thread(new Runnable() {
	public void run() {
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
		}
	}});
timerAccuracyThread.setDaemon(true);
timerAccuracyThread.start();
*/
        buenos = new ArrayList();
        malos = new ArrayList();

    }
    public static void main(String arg[]){
        Bichos b = new Bichos();
        b.cargarRecursos();
        b.inicializarJuego();
        b.loopPrincipal();
    }

    private void cargarRecursos(){
        try {
            fuente1 = new Fuente("recursos/imagenes/fuente.ttf", 20, false, false, Color.BLACK);
            fuente2 = new Fuente("recursos/imagenes/fuente.ttf", 30, false, false, Color.WHITE);
            fuente3 = new Fuente("recursos/imagenes/fuente.ttf", 30, false, false, Color.WHITE);
            ftGanador = new Fuente("recursos/imagenes/fuente.ttf", 50, false, false, Color.ORANGE);
        } catch (SlickException ex) {
            Logger.getLogger(Bichos.class.getName()).log(Level.SEVERE, null, ex);
        }

        fondo = new Recurso("recursos/imagenes/fondo.png");
        title = new Recurso("recursos/imagenes/title.png");
    bicho1tex = new Textura("recursos/imagenes/bicho1anim.png");
    bicho2tex = new Textura("recursos/imagenes/bicho2anim.png");
    defensa1tex = new Textura("recursos/imagenes/defensa1anim.png");
    defensa2tex = new Textura("recursos/imagenes/defensa2anim.png");
    proyectil1tex = new Textura("recursos/imagenes/proyectil1.png");
    proyectil2tex = new Textura("recursos/imagenes/proyectil2.png");
    bicho3tex = new Textura("recursos/imagenes/bicho3.png");
    defensa3tex = new Textura("recursos/imagenes/defensa3.png");
    Textura defensaMayorTex = new Textura("recursos/imagenes/adn.png");
    Textura bichoMayorTex = new Textura("recursos/imagenes/bichoFinal.png");
    barraSuperior = new Recurso("recursos/imagenes/barraSuperior.png");

    DefensaMayor.textura = defensaMayorTex;
    DefensaMayor.numX = 1;
    DefensaMayor.numY = 1;

    BichoMayor.textura = bichoMayorTex;
    BichoMayor.numX = 1;
    BichoMayor.numY = 1;

    Bicho1.textura = bicho1tex;
    Bicho1.numX = 10;
    Bicho1.numY = 5;
    Defensa1.textura = defensa1tex;
    Defensa1.numX= 10;
    Defensa1.numY = 5;
    Bicho2.textura = bicho2tex;
    Bicho2.numX = 9;
    Bicho2.numY = 4;
    Defensa2.textura = defensa2tex;
    Defensa2.numX = 9;
    Defensa2.numY = 5;
    Bicho3.textura = bicho3tex;
    Bicho3.numX = 6;
    Bicho3.numY = 7;
    Defensa3.textura = defensa3tex;
    Defensa3.numX = 12;
    Defensa3.numY = 5;

    adn = new DefensaMayor();
    chiefVirus = new BichoMayor();

    barra = new Recurso("recursos/imagenes/barraPuntos.png");

    texb1n = new Textura("recursos/imagenes/monocito.png");
    texb1d = new Textura("recursos/imagenes/monocito_deshabilitado.png");

    texb2n = new Textura("recursos/imagenes/anticuerpo.png");
    texb2d = new Textura("recursos/imagenes/anticuerpo_deshabilitado.png");

    texb3n = new Textura("recursos/imagenes/macrofago.png");
    texb3d = new Textura("recursos/imagenes/macrofago_deshabilitado.png");

    boton1 = new Recurso(texb1n);
    boton2 = new Recurso(texb2n);
    boton3 = new Recurso(texb3n);

    cargaBarra = new Recurso("recursos/imagenes/barraSobre.png");

    boton1.esEstatico(true);
    boton2.esEstatico(true);
    boton3.esEstatico(true);


    escenario = new Recurso("recursos/imagenes/escenario1.png");
    
    bicho1 = new Bicho1();
    defensa1 = new Defensa1();
    bicho2 = new Bicho2();
    defensa2 = new Defensa2();
    bicho3 = new Bicho3();


    }

    private void inicializarJuego(){
    juego.borrarRecursos();
    adn.puntosVida=1000;
    chiefVirus.puntosVida=1000;
    buenos.clear();
    malos.clear();
    proyectiles.clear();
    puntos=15;

    juego.agregarRecurso(escenario,0, 0);
    juego.agregarRecurso(adn, 0, 0);
    
    buenos.add(adn);

    juego.agregarRecurso(chiefVirus, 2000-chiefVirus.obtenerAnchura(), 0);

    malos.add(chiefVirus);
    juego.agregarRecurso(barra, 0, 150);
    barra.esEstatico(true);

    juego.agregarRecurso(fondo, 0,0);
    fondo.esEstatico(true);
    juego.agregarRecurso(barraSuperior, 0, 600-50);
    barraSuperior.esEstatico(true);
    juego.agregarRecurso(boton1, 0, 0);
    juego.agregarRecurso(boton2, 150, 0);
    juego.agregarRecurso(boton3, 300, 0);
   // juego.agregarRecurso(cargaBarra, 106, 150);
    //cargaBarra.escalar(0.1, 1,100,150);
    /*
    juego.agregarRecurso(colision2, 40, 200);
    juego.agregarRecurso(listaColisiones, 100, 200);
    juego.agregarRecurso(bicho1, 1300, 200);
  //  juego.agregarRecurso(defensa1, 500, 200);
   // juego.agregarRecurso(bicho2, 1600, 200);
    juego.agregarRecurso(defensa2, 200, 200);
    malos.add(bicho1);
   // malos.add(bicho2);
   // buenos.add(defensa1);
    buenos.add(defensa2);

    juego.agregarRecurso(bicho3, 600, 200);
    malos.add(bicho3);
*/
  


    }

    private void loopPrincipal(){
    siguienteTick = Utilidades.obtenerTiempo();
    ultimoPuntos = Utilidades.obtenerTiempo();
    ultimoEnemigo = Utilidades.obtenerTiempo();
    while(true){
   if(Utilidades.obtenerTiempo()>siguienteTick){
        switch(estado){
            case jugando:
                moverEscenario();
                moverElementos();
                escuchaTeclado();
                actualizarPuntosYEnemigos();

                if(chiefVirus.puntosVida<=0){
                    estado = estado.ganaJugador;
                }

                if(adn.puntosVida<=0){

                    estado = estado.ganaCPU;
                }

                break;

            case titulo:
                muestraTitulo();
                break;
            case ganaJugador:
                muestraGanador("Has curado las enfermedades!");
                break;
            case ganaCPU:
                muestraGanador("Has muerto!");
                break;

        }

        juego.actualizar();

        juego.asignarTitulo(""+juego.obtenerCuadrosPorSegundo());
        
        siguienteTick+=1000/ticks;
        }
   }
    }



}
