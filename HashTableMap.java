/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author Динара Асан
 * @param <K>
 * @param <V>
 */
public class HashTableMap<K, V> implements Map<K,V>, HashTableStats{
 private LinkedListQueue<KeyValuePair<K, V>>[] buckets;
    private int size;
    
    HashTableMap(int numBuckets){
        buckets = new LinkedListQueue[numBuckets];
        size = 0;
    }

    @Override
    public void define(K key, V value) {
        int pos = Math.abs(key.hashCode())%buckets.length;
        
        if(buckets[pos] == null){
            buckets[pos] = new LinkedListQueue();
        }

        boolean newNode = true;
        for(int i = 0; i < buckets[pos].getSize(); i++){
        	try{
        		KeyValuePair<K, V> tmp = buckets[pos].dequeue();
        		buckets[pos].enqueue(tmp);
        		if(tmp.getKey().equals(key)){
        			tmp.setValue(value);
        			newNode = false;
        			break;
        		}
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        	}
        }
        
        if(newNode){
        	buckets[pos].enqueue(new KeyValuePair(key, value));
        	size++;
        }

    }

    @Override
    public V getValue(K key) {
        int pos = Math.abs(key.hashCode())%buckets.length;
        
        if(buckets[pos] == null){                
        	return null;
        }
                               
        for(int i = 0; i < buckets[pos].getSize(); i++){
        	try{
        		KeyValuePair<K, V> tmp = buckets[pos].dequeue();
        		buckets[pos].enqueue(tmp);
        		if(tmp.getKey().equals(key)){
        			return tmp.getValue();
        		}
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        	}
        }

        return null;                                                                                                        
    }

    @Override
    public V remove(K key) {
        int pos = Math.abs(key.hashCode())%buckets.length;
        
        if(buckets[pos] == null){                
        	return null;
        }
                               
        for(int i = 0; i < buckets[pos].getSize(); i++){
        	try{
        		KeyValuePair<K, V> tmp = buckets[pos].dequeue();
        		if(tmp.getKey().equals(key)){
        			size--;
        			return tmp.getValue();
        		}
        		buckets[pos].enqueue(tmp);
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        	}
        }

        return null;                                                                                                           	
    }

    @Override
    public KeyValuePair<K, V> removeAny() throws Exception {
    	if(size == 0){
    		throw new Exception("The Map is Empty!");
    	}
    	KeyValuePair<K, V> result = null;
    	for(int i = 0; i < buckets.length; i++){
    		if(buckets[i] != null && buckets[i].getSize() > 0){
    			try{
    				result = buckets[i].dequeue();
    			}catch(Exception e){
    				System.out.println(e.getMessage());
    			}
    			break;
    		}
    	}
    	size--;
    	return result;                                                                          
    }

    @Override
    public int getSize() {                                                                                                             
    	return size;
    }

    @Override
    public void clear() {                                                                                                              
    	buckets = new LinkedListQueue[buckets.length];
    	size = 0;
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < buckets.length; i++){
            if(buckets[i] == null){
                result = result+i+":\n";
            }else{
                result = result+i+":\n"+buckets[i];
            }
        }
        return result;
    }

    @Override
    public int getNumberOfBuckets() {
        return buckets.length;
    }

    @Override
    public int getBucketSize(int index) throws Exception {
        if(index >= buckets.length){
            throw new Exception("Out of boundary!");
        }
        
        if(buckets[index] == null){
            return 0;
        }
        
        return buckets[index].getSize();
    }

    @Override
    public double getLoadFactor() {
        return (double) size/(double) buckets.length;
    }

    @Override
    public double getBucketSizeStandardDev() {
        double result = 0.0, k, factor = getLoadFactor();
        for(int i = 0; i < buckets.length; i++){
            if(buckets[i] == null){
                k = 0.0;
            }else{
                k = (double) buckets[i].getSize();
            }
            k = k-factor;
            result = result+(k*k);
        }
        
        k = (double) buckets.length;
        result = result*(1.0/k);
        
        return Math.sqrt(result);
    }

    @Override
    public String bucketsToString() {
        return toString();
    }
}
