object hellothread {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(64); 
  println("Welcome to the Scala worksheet");$skip(68); 
	
	def main(){
		var t = new HelloThread()
		t.start()
		t.run()
	};System.out.println("""main: ()Unit""")}
}

class HelloThread extends Thread{
	override def run(){
		print("Hello")
	}
}
