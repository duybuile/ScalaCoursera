object hellothread {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
	
	def main(){
		var t = new HelloThread()
		t.start()
		t.run()
	}                                         //> main: ()Unit
}

class HelloThread extends Thread{
	override def run(){
		print("Hello")
	}
}