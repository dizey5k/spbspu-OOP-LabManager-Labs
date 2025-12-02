package Lab3;

import Lab3.Animal.*;
import LabManager.LabInfo;
import LabManager.RunnableLab;

import java.util.*;

public class Lab3 implements RunnableLab {
    private final Scanner scanner;
    private final LabInfo labInfo;

    public Lab3() {
        this.scanner = new Scanner(System.in);
        this.labInfo = new LabInfo(3, "Generics and Wildcards",
                "Hierarchy animals with segregate with generics");
    }

    @Override
    public void run() {
        System.out.println("=== Lab 3: Generics and Wildcards ===");
        System.out.println("Demo method segregate for animals hie");

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    demonstrateBasicSegregation();
                    break;
                case 2:
                    demonstrateWildcardFlexibility();
                    break;
                case 3:
                    demonstrateMixedCollection();
                    break;
                case 4:
                    showAnimalHierarchy();
                    break;
                case 5:
                    demonstrateSortedSetAndComparators();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }
    }

    @Override
    public LabInfo getLabInfo() {
        return labInfo;
    }

    private void showMenu() {
        System.out.println("\n--- Lab 3 menu ---");
        System.out.println("1. base demo segregate");
        System.out.println("2. wildcards flexibility");
        System.out.println("3. mixed collection");
        System.out.println("4. show animal hie");
        System.out.println("5. SortedSet and Comparators");
        System.out.println("0. back to main menu");
        System.out.print("Choose option: ");
    }

    private int getChoice() {
        System.out.print("Choose option: ");
        String input = scanner.nextLine().trim(); // scanner.hasNext

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static <T extends Animal> void segregate(
            Collection<? extends T> srcCollection,
            Collection<? super Hedgehog> collection1,
            Collection<? super Manul> collection2,
            Collection<? super Lynx> collection3) {

        for (T animal : srcCollection) {
            if (animal instanceof Hedgehog) {
                collection1.add((Hedgehog) animal);
            } else if (animal instanceof Manul) {
                collection2.add((Manul) animal);
            } else if (animal instanceof Lynx) {
                collection3.add((Lynx) animal);
            }
        }
    }

    private static class NameComparator implements Comparator<Animal> {
        @Override
        public int compare(Animal a1, Animal a2) {
            return a1.getName().compareTo(a2.getName());
        }
    }

    private static class ReverseNameComparator implements Comparator<Animal> {
        @Override
        public int compare(Animal a1, Animal a2) {
            return a2.getName().compareTo(a1.getName()); // Обратный порядок
        }
    }

    private static class ClassNameComparator implements Comparator<Animal> {
        @Override
        public int compare(Animal a1, Animal a2) {
            int classCompare = a1.getClass().getSimpleName().compareTo(a2.getClass().getSimpleName());
            if (classCompare != 0) {
                return classCompare;
            }
            return a1.getName().compareTo(a2.getName());
        }
    }

    private void demonstrateBasicSegregation() {
        System.out.println("\n=== Base demo ===");

        Hedgehog hedgehog1 = new Hedgehog("HH-1");
        Hedgehog hedgehog2 = new Hedgehog("HH-2");
        Manul manul1 = new Manul("Manul-1");
        Manul manul2 = new Manul("Manul-2");
        Lynx lynx1 = new Lynx("Lynx-1");
        Lynx lynx2 = new Lynx("Lynx-2");

        System.out.println("Demo 1: segregate(erinaceidae, hedgehog, felidae, predator)");

        Collection<Mammal> mammals = Arrays.asList(hedgehog1, manul1, lynx1, hedgehog2);
        Collection<Erinaceidae> erinaceidae = new ArrayList<>();
        Collection<Felidae> felidae = new ArrayList<>();
        Collection<Predator> predators = new ArrayList<>();

        segregate(mammals, erinaceidae, felidae, predators);

        System.out.println("HH: " + erinaceidae.size() + " animals: " + getAnimalNames(erinaceidae));
        System.out.println("Fel: " + felidae.size() + " animals: " + getAnimalNames(felidae));
        System.out.println("Pred: " + predators.size() + " animals: " + getAnimalNames(predators));

        waitForEnter();
    }

    private void demonstrateWildcardFlexibility() {
        System.out.println("\n=== demo wildcard flexibility ===");

        Hedgehog hedgehog1 = new Hedgehog("HH-A");
        Manul manul1 = new Manul("Manul-A");
        Lynx lynx1 = new Lynx("Lynx-A");
        Manul manul2 = new Manul("Manul-B");

        System.out.println("Demo 2: segregate(predator, chordata, manuls, felidae)");

        Collection<Predator> predatorsSrc = Arrays.asList(manul1, lynx1, manul2);
        Collection<Chordata> chordata = new ArrayList<>();
        Collection<Manul> manuls = new ArrayList<>();
        Collection<Felidae> felidae2 = new ArrayList<>();

        segregate(predatorsSrc, chordata, manuls, felidae2);

        System.out.println("CD: " + chordata.size() + " animals: " + getAnimalNames(chordata));
        System.out.println("Manuls: " + manuls.size() + " animals: " + getAnimalNames(manuls));
        System.out.println("FD: " + felidae2.size() + " animals: " + getAnimalNames(felidae2));

        System.out.println("\nDemo 3: segregate(erinaceidee, insectivores, predator, predator)");

        Collection<Erinaceidae> erinaceidaeSrc = Arrays.asList(hedgehog1, new Hedgehog("HH-B"));
        Collection<Insectivore> insectivores = new ArrayList<>();
        Collection<Predator> predators1 = new ArrayList<>();
        Collection<Predator> predators2 = new ArrayList<>();

        segregate(erinaceidaeSrc, insectivores, predators1, predators2);

        System.out.println("Erin: " + insectivores.size() + " animals: " + getAnimalNames(insectivores));
        System.out.println("Pred (collection 1): " + predators1.size() + " animals: " + getAnimalNames(predators1));
        System.out.println("Pred (collection 2): " + predators2.size() + " animals: " + getAnimalNames(predators2));

        waitForEnter();
    }

    private void demonstrateMixedCollection() {
        System.out.println("\n=== Demo with mixed collection ===");

        Hedgehog hedgehog1 = new Hedgehog("HH");
        Manul manul1 = new Manul("Manul");
        Lynx lynx1 = new Lynx("Lynx");
        Hedgehog hedgehog2 = new Hedgehog("HH-2");
        Manul manul2 = new Manul("Manul-2");
        Lynx lynx2 = new Lynx("Lynx-2");

        Collection<Animal> mixedAnimals = Arrays.asList(hedgehog1, manul1, lynx1, hedgehog2, manul2, lynx2);
        Collection<Hedgehog> hedgehogs = new ArrayList<>();
        Collection<Manul> manulsCollection = new ArrayList<>();
        Collection<Lynx> lynxes = new ArrayList<>();

        segregate(mixedAnimals, hedgehogs, manulsCollection, lynxes);

        System.out.println("HH: " + hedgehogs.size() + " animals: " + getAnimalNames(hedgehogs));
        System.out.println("Manul: " + manulsCollection.size() + " animals: " + getAnimalNames(manulsCollection));
        System.out.println("Lynx: " + lynxes.size() + " animals: " + getAnimalNames(lynxes));

        waitForEnter();
    }

    private void demonstrateSortedSetAndComparators() {
        System.out.println("\n=== SortedSet and Comparators Demo ===");

        Hedgehog hedgehog1 = new Hedgehog("Charlie");
        Manul manul1 = new Manul("Alice");
        Lynx lynx1 = new Lynx("Bob");
        Hedgehog hedgehog2 = new Hedgehog("David");
        Manul manul2 = new Manul("Eve");

        System.out.println("Original animals (unsorted):");
        List<Animal> animals = Arrays.asList(hedgehog1, manul1, lynx1, hedgehog2, manul2);
        System.out.println(getAnimalNames(animals));

        System.out.println("\n1. TreeSet with natural ordering (by name):");
        SortedSet<Animal> sortedByName = new TreeSet<>(new NameComparator());
        sortedByName.addAll(animals);
        System.out.println("Sorted by name: " + getAnimalNames(sortedByName));

        System.out.println("\n2. TreeSet with reverse name comparator:");
        SortedSet<Animal> sortedByReverseName = new TreeSet<>(new ReverseNameComparator());
        sortedByReverseName.addAll(animals);
        System.out.println("Sorted by reverse name: " + getAnimalNames(sortedByReverseName));

        System.out.println("\n3. TreeSet with class+name comparator:");
        SortedSet<Animal> sortedByClassAndName = new TreeSet<>(new ClassNameComparator());
        sortedByClassAndName.addAll(animals);
        System.out.println("Sorted by class and name: " + getAnimalNames(sortedByClassAndName));

        System.out.println("\n4. Segregate with SortedSet collections:");

        SortedSet<Animal> sourceSet = new TreeSet<>(new NameComparator());
        sourceSet.addAll(Arrays.asList(hedgehog1, manul1, lynx1, hedgehog2, manul2));

        SortedSet<Hedgehog> hedgehogSet = new TreeSet<>(new NameComparator());
        SortedSet<Manul> manulSet = new TreeSet<>(new ReverseNameComparator()); // Другой компаратор для демонстрации
        SortedSet<Lynx> lynxSet = new TreeSet<>(new NameComparator());

        segregate(sourceSet, hedgehogSet, manulSet, lynxSet);

        System.out.println("Source SortedSet: " + getAnimalNames(sourceSet));
        System.out.println("Hedgehogs SortedSet: " + getAnimalNames(hedgehogSet));
        System.out.println("Manuls SortedSet (reverse order): " + getAnimalNames(manulSet));
        System.out.println("Lynx SortedSet: " + getAnimalNames(lynxSet));

        System.out.println("\n5. SortedSet methods demonstration:");
        System.out.println("First element: " + sourceSet.first().getName());
        System.out.println("Last element: " + sourceSet.last().getName());

        Animal middleElement = (Animal) sourceSet.toArray()[sourceSet.size() / 2];
        SortedSet<Animal> headSet = sourceSet.headSet(middleElement);
        System.out.println("HeadSet (before " + middleElement.getName() + "): " + getAnimalNames(headSet));

        waitForEnter();
    }

    private void showAnimalHierarchy() {
        System.out.println("\n=== Animal hierarchy (all abstract except Lynx, Manul, Hedgehog) ===");
        System.out.println("Animal");
        System.out.println("  L__ Chordata");
        System.out.println("        L__ Mammal");
        System.out.println("              |-- Predator");
        System.out.println("              |     L__ Felidae");
        System.out.println("              |           |-- Lynx");
        System.out.println("              |           L__ Manul");
        System.out.println("              L__ Insectivore");
        System.out.println("                    L__ Erinaceidae");
        System.out.println("                          L__ Hedgehog");

        waitForEnter();
    }

    private String getAnimalNames(Collection<? extends Animal> animals) {
        return animals.stream()
                .map(Animal::getName)
                .toList()
                .toString();
    }

    private void waitForEnter() {
        System.out.println("\nEnter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}