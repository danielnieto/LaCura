/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;

import java.util.ArrayList;
import org.niktin.recursos.Recurso;
import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;


public class Ser extends Recurso{

    long tiempoUltimoMovimiento;
    long tiempoActual;
    long esperaEntreAtaque=0;
    long tiempoUltimoAtaque;
    int sentidoAnimacion=1;
    double puntosAtaque=25;
    public double puntosVida=100;
    float factorRandomAtaque = .3f;

    protected boolean atacar;
    double velocidadDeDesplazamiento=3f;
    public enum Estado{desplazandose, atacando};
    public enum Bando{bueno,malo};
    public Estado estado = Estado.desplazandose;
    public ArrayList<Vacio> listaColisiones;
    public Bando bando;
    Ser(Textura textura, int numX, int numY) {
        super(textura, numX,numY);
        listaColisiones = new ArrayList();
    }


    protected boolean colisionaConAlgunEnemigo() {
        if(bando==Bando.bueno){
        for(Ser enemigo: Bichos.malos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                for(Vacio colision: listaColisiones){
                if(colision.colisionaCon(colisionaObjetivo))
                    return true;
                }
            }
        }
        
        }else if(bando==Bando.malo){
        for(Ser enemigo: Bichos.buenos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                for(Vacio colision: listaColisiones){
                if(colision.colisionaCon(colisionaObjetivo))
                    return true;
                }
            }
        }

        }
        return false;
    }

 protected Ser conQueEnemigoColisiona() {
        if(bando==Bando.bueno){
        for(Ser enemigo: Bichos.malos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                for(Vacio colision: listaColisiones){
                if(colision.colisionaCon(colisionaObjetivo))
                    return enemigo;
                }
            }
        }

        }else if(bando==Bando.malo){
        for(Ser enemigo: Bichos.buenos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                for(Vacio colision: listaColisiones){
                if(colision.colisionaCon(colisionaObjetivo))
                    return enemigo;
                }
            }
        }

        }
        return null;
    }

   

   public void actualizarEstado(){
      

    }


    public void desplazar(int numCuadrosDesplazaInicio, int numCuadrosDesplazaFinal, float velocidad){


    }

     public void atacar(int numCuadrosAtacaInicio, int numCuadrosAtacaFinal, float velocidad){

    }

    public int aleatorio(int max,int min){
       return (int)(Math.random()*(max-min))+min;
    }

}
