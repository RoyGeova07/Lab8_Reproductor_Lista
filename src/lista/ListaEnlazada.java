/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lista;

/**
 *
 * @author royum
 */
public class ListaEnlazada {
    
    private Nodo cabeza;
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    public void agregarCancion(Cancion cancion) {
        Nodo nuevo = new Nodo(cancion);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamaño++;
    }

    public Cancion obtenerCancion(int indice) {
        if (indice < 1 || indice > tamaño) {
            return null;
        }
        Nodo actual = cabeza;
        for (int i = 1; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getCancion();
    }

    public void listarCanciones() {
        Nodo actual = cabeza;
        int indice = 1;
        while (actual != null) {
            Cancion cancion = actual.getCancion();
            System.out.println(indice + ". " + cancion.getTitulo() + " - " + cancion.getArtista());
            actual = actual.getSiguiente();
            indice++;
        }
    }

    public int tamaño() {
        return tamaño;
    }

    public Nodo getCabeza() {
        return cabeza;
    }
    
    
}