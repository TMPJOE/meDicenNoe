package dev.josedegracia.jamboardReplacement.board;

import java.util.Random;

public class AnonymounsUser {
    Random randomId = new Random();
    int id;
    String name;
    String[] animalNames = {
            "Antelope",
            "Bear",
            "Camel",
            "Cheetah",
            "Dolphin",
            "Elephant",
            "Fox",
            "Giraffe",
            "Hippopotamus",
            "Jaguar",
            "Kangaroo",
            "Koala",
            "Lion",
            "Monkey",
            "Octopus",
            "Ostrich",
            "Panda",
            "Parrot",
            "Peacock",
            "Penguin",
            "Pig",
            "Rabbit",
            "Rhinoceros",
            "Snake",
            "Tiger",
            "Turtle",
            "Walrus",
            "Whale",
            "Wolf",
            "Zebra",
            "Aardvark",
            "Iguana",
            "Manatee",
            "Narwhal",
            "Platypus"
    };


    public  int generateId(){
        int id = randomId.nextInt();
        return id;
    }

    public AnonymounsUser() {
        this.id = generateId();
        this.name = animalNames[randomId.nextInt(animalNames.length)];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
