public class Linkedbag<E> {
    private Node head;
    private Node tail;
    private int numElement;

    public Linkedbag() {
        head = null;
        tail = null;
        numElement = 0;
    }

    public void add(E newElement) {
        if (tail == null) // is empty
        {
            head = new Node<E>(newElement, null);
            tail = head;
        } else {
            tail.setLink(new Node<E>(newElement, null));
            tail = tail.getLink();
        }
        numElement++;
    }

    public boolean exists(E target) {
        boolean found = false;
        Node<E> cursor = head;

        while (cursor != null && !found) {
            if (cursor.getData() == target || cursor.getData().equals(target)) {
                found = true;
            } else {
                cursor = cursor.getLink();
            }
        }
        return found;
    }

    public int countOccurences(E target) {
        int numFound = 0;
        Node<E> cursor = head;

        while (cursor != null) {
            if (cursor.getData() == target || cursor.getData().equals(target)) {
                numFound++;
            }

            cursor = cursor.getLink();

        }
        return numFound;
    }

    public boolean remove(E target) {
        Node<E> cursor = head, previous = null;
        boolean found = false;

        while (cursor != null && !found) {
            if (cursor.getData() == target || cursor.getData().equals(target)) {
                found = true;
            } else {
                previous = cursor;
                cursor = cursor.getLink();
            }
        }
        if (found && cursor != null) {
            if (previous == null) {
                head = head.getLink();
            } else {
                previous.setLink(cursor.getLink());
            }
            if (tail == cursor) {
                tail = previous;

            }
            numElement--;
        }
        return found;
    }

    public int getNumElement() {
        return numElement;
    }

    public Lister<E> iterator() {
        // declare variables
        Node headOfListToReturn; // beginning of new "copied" list
        Node cursorOfListToCopy; // active node of list to copy
        Node lastNodeOfListToReturn; // end of new "copied" list

        // establish the copied list
        headOfListToReturn = null;

        if (head != null) {
            // create the head of the new list
            headOfListToReturn = new Node(head.getData(), null);
            // use lastNodeOfListToReturn as a pointer to the last node in the copied list
            lastNodeOfListToReturn = headOfListToReturn;
            // use currentCursor as the pointer to the existing list
            cursorOfListToCopy = head.getLink();
            // if we have a node...
            while (cursorOfListToCopy != null) {
                // create a new node from the end of the new list
                lastNodeOfListToReturn.setLink(new Node(cursorOfListToCopy.getData(), null));
                // move lastNodeOfListToReturn to the new last node
                lastNodeOfListToReturn = lastNodeOfListToReturn.getLink();
                // move the cursorOfListToCopy to the next node
                cursorOfListToCopy = cursorOfListToCopy.getLink();
            }
        }

        return new Lister(headOfListToReturn);
    }

}
