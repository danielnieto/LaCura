/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;


public class Bicho3 extends Ser{

    public static Textura textura;
    static int numX, numY;
    

    Bicho3() {
        super(textura, numX,numY);

        Vacio colision = new Vacio(anchura-(anchura*.6),altura-(altura*.1));
        colision.asignarX(x + colision.obtenerAnchura()/2);
        colision.asignarY(y);
        tiempoUltimoMovimiento = Utilidades.obtenerTiempo();
        this.agregarHijo(colision);
        listaColisiones.add(colision);
        bando = super.bando.malo;
        esperaEntreAtaque=100;

        puntosVida=80;
        puntosAtaque=10;

        sentidoAnimacion=1;
        velocidadDeDesplazamiento=4f;
        estado = Estado.desplazandose;

    }

   
    @Override
   public void actualizarEstado(){

        if(!colisionaConAlgunEnemigo()){
            if(((obtenerFotogramaActual()>15&&sentidoAnimacion==1)||(obtenerFotogramaActual()<15&&sentidoAnimacion==-1))&&(estado!=estado.atacando)&&(obtenerFotogramaActual()!=30)){
                
                asignarX(x-velocidadDeDesplazamiento);
            }

        }

         if(colisionaConAlgunEnemigo()){
            if(obtenerFotogramaActual()!=1&&obtenerFotogramaActual()!=30&&estado == estado.desplazandose){
                 desplazar(1, 30,.02f);
            }else{
                 atacar(30, 40,.02f);
            }
        }else{
        if(obtenerFotogramaActual()!=30&&estado == estado.atacando){
                atacar(30, 40,.02f);
            }else{
                desplazar(1, 30,.02f);
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
                    sentidoAnimacion=-1;

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



}
