package javathreads;

class Item{
	int num;
	boolean valueSet = false;
	public synchronized void put(int num) {
		while(valueSet) {
			try {
				wait();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		this.num = num;
		System.out.println("Put :"+this.num);
		valueSet = true;
		notify();
	}
	public synchronized void get() {
		while(!valueSet) {
			try {
				wait();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		System.out.println("Get :"+this.num);
		valueSet = false;
		notify();
	}
}
class Producer implements Runnable{
	Item item;
	public Producer(Item item) {
		this.item = item;
		Thread t1 = new Thread(this);
		t1.start();
	}
	public void run() {
		int i = 0;
		while(true) {
			item.put(i++);
			try {
				Thread.sleep(1000);
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
class Consumer implements Runnable{
	Item item;
	public Consumer(Item item) {
		this.item = item;
		Thread t2 = new Thread(this);
		t2.start();
	}
	public void run() {
		while(true) {
			item.get();
			try {
				Thread.sleep(2000);
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
public class ProducerConsumer {

	public static void main(String[] args) {
		Item item = new Item();
		new Producer(item);
		new Consumer(item);
	}

}
