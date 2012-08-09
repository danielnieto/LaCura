/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import org.niktin.juego.Juego;
import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;


public class Bicho2 extends Ser{

    public static Textura textura;
    static int numX, numY;
    

    Bicho2() {
        super(textura, numX,numY);

        Vacio colision = new Vacio(anchura-(anchura*.3),altura-(altura*.3));
        colision.asignarX(x + colision.obtenerAnchura()/2);
        colision.asignarY(y);
        tiempoUltimoMovimiento = Utilidades.obtenerTiempo();
        this.agregarHijo(colision);
        listaColisiones.add(colision);
        bando = super.bando.malo;

        puntosVida=150;
        puntosAtaque=50;
        

        sentidoAnimacion=1;
        velocidadDeDesplazamiento=1f;
        estado = Estado.desplazandose;
        esperaEntreAtaque = 2800;

    }

   
    @Override
   public void actualizarEstado(){

        if(!colisionaConAlgunEnemigo()&&!algunoEnRango(300)){
            asignarX(x-velocidadDeDesplazamiento);
            desplazar(1,30,.02f);
        }else if(!colisionaConAlgunEnemigo()&&algunoEnRango(300)){
            atacar();
            desplazar(1,30,.02f);
        
        }else{
            desplazar(1,30,.02f);
        
        }



/*         if(colisionaConAlgunEnemigo()){
            if(obtenerFotogramaActual()!=1&&obtenerFotogramaActual()!=30&&estado == estado.desplazandose){
                 desplazar(1, 30,.02f);
            }else{
                 atacar(30, 50,.02f);
            }
        }else{
        if(obtenerFotogramaActual()!=30&&estado == estado.atacando){
                atacar(30, 50,.02f);
            }else{
                desplazar(1, 30,.02f);
            }


        }

*/
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
        for(Ser enemigo:Bichos.buenos){
            if(x-(enemigo.obtenerX()+enemigo.obtenerAnchura())<rango){
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
        }
        
        if(tiempoActual-tiempoUltimoMovimiento>1000*velocidad&&atacar){
            
            if(sentidoAnimacion==1){
                super.fotogramaSiguiente();
            if(super.obtenerFotogramaActual()==numCuadrosAtacaFinal){
                conQueEnemigoColisiona().puntosVida-=puntosAtaque+Utilidades.aleatorio(puntosAtaque*factorRandomAtaque,-puntosAtaque*factorRandomAtaque);
                
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
