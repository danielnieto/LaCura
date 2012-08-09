/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import org.niktin.juego.Juego;
import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;


public class Defensa2 extends Ser{

    public static Textura textura;
    static int numX, numY;
    

    Defensa2() {
        super(textura, numX,numY);

        Vacio colision = new Vacio(anchura-(anchura*.4),altura-(altura*.1));
        colision.asignarX(x + colision.obtenerAnchura()/2);
        colision.asignarY(y);
        tiempoUltimoMovimiento = Utilidades.obtenerTiempo();
        this.agregarHijo(colision);
        listaColisiones.add(colision);
        bando = super.bando.bueno;
        
        puntosVida=150;
        puntosAtaque=50;

        sentidoAnimacion=1;
        velocidadDeDesplazamiento=1f;
        estado = Estado.desplazandose;
        esperaEntreAtaque = 1880;

    }

   
    @Override
   public void actualizarEstado(){

        if(!colisionaConAlgunEnemigo()&&!algunoEnRango(300)){
            asignarX(x+velocidadDeDesplazamiento);

            if(obtenerFotogramaActual()!=30&&obtenerFotogramaActual()!=40&&estado == estado.atacando){

                atacar(30,40,.02f);
            }else{

                desplazar(1,30,.02f);
            }
        }else if(!colisionaConAlgunEnemigo()&&algunoEnRango(300)){

          if(obtenerFotogramaActual()!=1&&obtenerFotogramaActual()!=29&&estado == estado.desplazandose){

                desplazar(1,30,.02f);
              
            }else{
                atacar(30,40,.02f);
              
            }

                //atacar(30,40,.01f);
            
            
          //  desplazar(1,30,.02f);
        
        }else{
         //    atacar(30,40,.03f);
           if(obtenerFotogramaActual()!=30&&estado == estado.atacando){
                atacar(30,40,.02f);
            }else{

                desplazar(1,30,.02f);
            }
        }


        /*if(!colisionaConAlgunEnemigo()&&!algunoEnRango(800)){
            asignarX(x+velocidadDeDesplazamiento);
        }
        */


         /*if(!colisionaConAlgunEnemigo()&&algunoEnRango(800)){
            if(obtenerFotogramaActual()!=1&&obtenerFotogramaActual()!=30&&estado == estado.desplazandose){
                 desplazar(1, 30,.02f);

            }else{
                 atacar(31, 40,.02f);
                
            }
        }else{
        if(obtenerFotogramaActual()!=30&&estado == estado.atacando){
                atacar(30, 40,.02f);
                 
            }else{
                desplazar(1, 30,.02f);
                
            }


        }*/

        
    }


    @Override
    public void desplazar(int numCuadrosDesplazaInicio, int numCuadrosDesplazaFinal, float velocidad){
        if(estado!=Estado.desplazandose){
            super.asignarFotogramaActual(numCuadrosDesplazaInicio);
            estado = Estado.desplazandose;
            sentidoAnimacion=1;
        }

        tiempoActual = Utilidades.obtenerTiempo();
        if(tiempoActual-tiempoUltimoMovimiento>1000*velocidad){
            if(sentidoAnimacion==1){
                super.fotogramaSiguiente();
                if(super.obtenerFotogramaActual()==numCuadrosDesplazaFinal)
                    asignarFotogramaActual(numCuadrosDesplazaInicio+1);

            }
            tiempoUltimoMovimiento=tiempoActual;
        }

    }

    private boolean algunoEnRango(int rango){
        for(Ser enemigo:Bichos.malos){
            if((enemigo.obtenerX()-(x+anchura))<rango){
                return true;
            }
        }
        return false;


    }

    @Override
     public void atacar(int numCuadrosAtacaInicio, int numCuadrosAtacaFinal, float velocidad){
         if(estado!=Estado.atacando){
            super.asignarFotogramaActual(numCuadrosAtacaInicio);
            estado = Estado.atacando;
            sentidoAnimacion=1;
        }



         tiempoActual = Utilidades.obtenerTiempo();
        if(tiempoActual-tiempoUltimoAtaque>esperaEntreAtaque){
            atacar=true;
        }else{
            atacar=false;
            desplazar(1,30,.02f);
            
        }


        
        if(tiempoActual-tiempoUltimoMovimiento>1000*velocidad&&atacar){
            
            if(sentidoAnimacion==1){
                super.fotogramaSiguiente();
            if(super.obtenerFotogramaActual()==numCuadrosAtacaFinal){
            //    conQueEnemigoColisiona().puntosVida-=puntosAtaque+Utilidades.aleatorio(puntosAtaque*factorRandomAtaque,-puntosAtaque*factorRandomAtaque);

            Proyectil proyectil2 = new Proyectil(Bichos.proyectil2tex);
            proyectil2.puntosAtaque = puntosAtaque;
            proyectil2.velocidadDeMovimiento=12;
            proyectil2.bando = proyectil2.bando.bueno;
             Juego.agregarRecurso(proyectil2, x+ anchura-(anchura*.3), y + (altura/2));
             Bichos.proyectiles.add(proyectil2);

                sentidoAnimacion=-1;
                
            }
            }else{
                
                super.fotogramaAnterior();
                if(super.obtenerFotogramaActual()==numCuadrosAtacaInicio){
                        sentidoAnimacion=1;
                        tiempoUltimoAtaque = tiempoActual;
                }
                           
                
            }
            tiempoUltimoMovimiento=tiempoActual;
        }
         

    }

    private void atacar() {
        tiempoActual = Utilidades.obtenerTiempo();
        if(tiempoActual-tiempoUltimoAtaque>esperaEntreAtaque){
            Proyectil proyectil = new Proyectil(Bichos.proyectil1tex);
            proyectil.puntosAtaque = puntosAtaque;
            proyectil.velocidadDeMovimiento=12;
            proyectil.bando = proyectil.bando.malo;
             Juego.agregarRecurso(proyectil, x, y + (altura/2));
             Bichos.proyectiles.add(proyectil);
        tiempoUltimoAtaque = tiempoActual;
        }
    }



}
