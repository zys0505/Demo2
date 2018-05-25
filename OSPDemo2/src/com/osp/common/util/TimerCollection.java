package com.osp.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class TimerCollection<T> implements List<T>{
	
	public TimerCollection() {
		new TimerCollection<T>(DEFAULT_TIME);
	}
	
	public TimerCollection(long time) {
		this.time = time;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				timerClear();
			}
		}, 0, 1000);
	}
	
	private static final long DEFAULT_TIME = (long) (60*1000);
	private Long time = DEFAULT_TIME;
	private List<Node> list = new ArrayList<>();
	/**
	 * @author liudonghe
	 *
	 */
	class Node {
		private T t;
		private Date date;
		public T getT() {
			return t;
		}
		public void setT(T t) {
			this.t = t;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		@Override
		public int hashCode() {
			return t.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if(obj == null){
				return false;
			}
			if(!obj.getClass().equals(this.getClass())){
				return false;
			}
			Node n = (Node)obj;
			if(this.getT().equals(n.getT())){
				return true;
			}
			return false;
		}
		private TimerCollection getOuterType() {
			return TimerCollection.this;
		}
		@Override
		public String toString() {
			return "Node [t=" + t + "]";
		}
	}
	private synchronized void timerClear(){
		Iterator<TimerCollection<T>.Node> iterator = list.iterator();
		while(iterator.hasNext()){
			TimerCollection<T>.Node next = iterator.next();
			long x = System.currentTimeMillis()-next.getDate().getTime();
			if(x<time){
				iterator.remove();
			}
		}
	}
	
	@Override
	public int size() {
		return list.size();
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public synchronized boolean contains(Object o) {
		Node node = new Node();
		node.setT((T)o);
		System.out.println("coll:"+list);
		return list.contains(node);
	}
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public synchronized boolean add(T e) {
		Node node = new Node();
		node.setDate(new Date());
		node.setT(e);
		return list.add(node);
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		list.clear();
	}
	@Override
	public T get(int index) {
		return list.get(index).getT();
	}
	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public synchronized void add(int index, T element) {
		Node node = new Node();
		node.setDate(new Date());
		node.setT(element);
		list.add(node);
	}
	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
