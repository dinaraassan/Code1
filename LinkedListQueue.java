package map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dinara Assan
 * @param <T>
 */
public class LinkedListQueue<T> implements Queue<T> {

    private Node<T> front;
    private Node<T> back;
    int size;
    
    public LinkedListQueue(){
        front = null;
        back = null;
        size = 0;
    }
    @Override
    public void enqueue(T value) {
        Node<T> temp = new Node (value);
        if(size == 0){
            front = back = temp;
            front.setLink(null);
            back.setLink(null);
        }else{
            back.setLink(temp);
            back = temp;
            back.setLink(null);
        }
        size++;
    }

    @Override
    public T dequeue() throws Exception {
        if(size == 0){
            throw new Exception("The queue is empty!");
        }else{
            T result = front.getValue();
            
            if(size == 1){
                front = null;
                back = null;
                size--;
            }else{
                front = front.getLink();
                size--;
            }
            return result;
        }
    }

    @Override
    public int getSize() {
        
        return size;
    }

    @Override
    public void clear() {
        
        front = null;
        back = null;
        size = 0;
    }
    
    @Override
    public String toString(){
        String result = "";
        if (this.getSize() == 0) {
            return "Empty Queue";
        }
        result += " " + "\n";
        Node<T> temp = front;
        while(temp  != null){
            result += temp.getValue() + " ";
            temp = temp.getLink();
        }
        return result;
    }
}
