/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;


public class Defensa3 extends Ser{

    public static Textura textura;
    static int numX, numY;
    

    Defensa3() {
        super(textura, numX,numY);

        Vacio colision = new Vacio(anchura-(anchura*.3),altura-(altura*0));
        colision.asignarX(x + colision.obtenerAnchura()/2);
        colision.asignarY(y);
        tiempoUltimoMovimiento = Utilidades.obtenerTiempo();
        this.agregarHijo(colision);
        listaColisiones.add(colision);
        bando = super.bando.bueno;
        esperaEntreAtaque=0;

        puntosVida=80;
        puntosAtaque=10;

        sentidoAnimacion=1;
        velocidadDeDesplazamiento=2f;
        estado = Estado.desplazandose;

    }

   
    @Override
   public void actualizarEstado(){

        if(!colisionaConAlgunEnemigo()){
            asignarX(x+velocidadDeDesplazamiento);
        }

         if(colisionaConAlgunEnemigo()){
            if(obtenerFotogramaActual()!=1&&obtenerFotogramaActual()!=40&&estado == estado.desplazandose){
                 desplazar(1, 40,.01f);
            }else{
                 atacar(40, 55,.01f);
            }
        }else{
        if(obtenerFotogramaActual()!=40&&estado == estado.atacando){
                atacar(40, 55,.01f);
            }else{
                desplazar(1, 40,.01f);
            }


        }


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
                    
                        asignarFotogramaActual(1);
                    //sentidoAnimacion=-1;

            }else{
                super.fotogramaAnterior();
                if(super.obtenerFotogramaActual()==numCuadrosDesplazaInicio)
                    sentidoAnimacion=1;
            }
            tiempoUltimoMovimiento=tiempoActual;
        }

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
        }
        
        if(tiempoActual-tiempoUltimoMovimiento>1000*velocidad&&atacar){
            
            if(sentidoAnimacion==1){
                super.fotogramaSiguiente();
            if(super.obtenerFotogramaActual()==numCuadrosAtacaFinal){
                Ser enemigo = conQueEnemigoColisiona();
                if(enemigo!=null)
                      enemigo.puntosVida-=puntosAtaque+Utilidades.aleatorio(puntosAtaque*factorRandomAtaque,-puntosAtaque*factorRandomAtaque);
               
                asignarFotogramaActual(numCuadrosAtacaInicio);
                // sentidoAnimacion=-1;
                
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

}
