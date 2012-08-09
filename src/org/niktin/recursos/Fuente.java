/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.niktin.recursos;

import org.lwjgl.opengl.Display;
import org.niktin.juego.Juego;
import java.awt.Color;
import java.awt.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import static org.lwjgl.opengl.GL11.*;


/**
 *
 * @author Daniel
 */
public class Fuente{
    UnicodeFont uFont;
    int tamano;
    boolean italica=false,negrita=false;
    Color color = java.awt.Color.WHITE;
    String texto;
    int x;
    int y;
    boolean cargado=false;

    public Fuente(String fuente, int tamano, boolean italica, boolean negrita) throws SlickException{
        this.tamano = tamano;
        this.italica = italica;
        this.negrita = negrita;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;

    }

    public Fuente(Font fuente, int tamano, boolean italica, boolean negrita) throws SlickException{
        this.tamano = tamano;
        this.italica = italica;
        this.negrita = negrita;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;

    }

    public Fuente(String fuente, int tamano) throws SlickException{
        this.tamano = tamano;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public Fuente(Font fuente, int tamano) throws SlickException{
        this.tamano = tamano;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public Fuente(String fuente, int tamano, Color color) throws SlickException{
        this.tamano = tamano;
        this.color = color;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public Fuente(Font fuente, int tamano, Color color) throws SlickException{
        this.tamano = tamano;
        this.color = color;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public Fuente(String fuente, int tamano, boolean italica, boolean negrita,Color color) throws SlickException{
        this.tamano = tamano;
        this.italica = italica;
        this.negrita = negrita;
        this.color = color;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public Fuente(Font fuente, int tamano, boolean italica, boolean negrita, Color color) throws SlickException{
        this.tamano = tamano;
        this.italica = italica;
        this.negrita = negrita;
        this.color = color;

        uFont = new UnicodeFont(fuente , this.tamano, this.italica, this.negrita);
        uFont.addAsciiGlyphs();
        uFont.addGlyphs(400, 600);
        uFont.getEffects().add(new ColorEffect(this.color));
        uFont.loadGlyphs();
        cargado = true;
    }

    public void render(){
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, Juego.obtenerAnchura(), Juego.obtenerAltura(), 0, 1, -1);
    uFont.drawString(x, y, texto);

    glLoadIdentity();
    glOrtho(0, Juego.obtenerAnchura(), 0,Juego.obtenerAltura(), 1, -1);
    glPopMatrix();

    
    
    }

    public void asignarTexto(String texto){
        this.texto = texto;
    }

    public void asignarX(int x){
        this.x = x;
    }

    public void asignarY(int y){
        this.y = Juego.obtenerAltura()-y- uFont.getLineHeight();
    }

    public String obtenerTexto(){
        return texto;
    }

    public int obtenerX(){
        return x;
    }

    public int obtenerY(){
        return y;
    }

    public int obtenerAnchura(String texto){
        return uFont.getWidth(texto);
    }

    public int obtenerAltura(String texto){
        return uFont.getHeight(texto);
    }

    public boolean cargaCompleta(){
        return cargado;
    }

}
