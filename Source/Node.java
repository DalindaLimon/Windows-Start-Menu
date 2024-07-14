public class Node<E> {
    E data;
    Node<E> link;

    public Node(E initialData, Node<E> initialLink) {
        data = initialData;
        link = initialLink;
    }

    public E getData() {
        return data;
    }

    public Node<E> getLink() {
        return link;
    }

    public void setData(E newData) {
        data = newData;
    }

    public void setLink(Node<E> newLink) {
        link = newLink;
    }

    public String toString() {
        return data.toString();
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof Node<?>) {
            Node<E> otherNode = (Node<E>) other;
            if (this.data == null) {
                return otherNode.data == null;
            }
            return this.data.equals(otherNode.data);
        }
        return false;
    }
}
