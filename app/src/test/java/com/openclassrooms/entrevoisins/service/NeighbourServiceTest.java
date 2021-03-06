package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNewNeighbour(){
        // creation d'un nouveau voisin
        Neighbour newNeighbour = new Neighbour(13,"Pierre","https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",  "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..",false);
        // ajout du voisin a la liste
        service.createNeighbour(newNeighbour);
        // verification que la liste des voisins contient le nouveau voisin ;
        assertTrue(service.getNeighbours().contains(newNeighbour));

    }


    @Test
    public void addFavoriteNeighbour (){
        // recuperation de la liste des voisins
        List<Neighbour> neighbours = service.getNeighbours();
        // recuperation du voisin qu'on souhaite ajouter en favoris
        Neighbour neighbourFavorite = service.getNeighbours().get(0);
        service.addFavorite(neighbourFavorite);
        // recuperation de la liste des voisins Favoris
        List<Neighbour> neighboursFavorite = neighbours.stream().filter(Neighbour::isFavorite).collect(toList());
        // verification que la taille correspond bien a 1;
        assertEquals(1,neighboursFavorite.size());
        // on verifie que le voisin present dans la liste est bien marqué en favoris
        assertTrue(neighbours.stream().map(Neighbour::isFavorite).collect(Collectors.toList()).contains(neighbourFavorite.isFavorite()));


    }

    @Test
    public void removeFavoriteNeighbour (){
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourFavorite = service.getNeighbours().get(0);
        service.addFavorite(neighbourFavorite);
        // Suppression du voisin des Favoris
        service.removeFavorite(neighbourFavorite);
        // recuperation de la liste des voisins Favoris
        List<Neighbour> neighboursFavorite = neighbours.stream().filter(Neighbour::isFavorite).collect(toList());
        // verification que la taille correspond bien a 0;
        assertEquals(0,neighboursFavorite.size());

    }



}
