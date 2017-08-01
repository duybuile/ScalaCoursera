package caseclass

object PatternMatching {
  
  abstract class Notification
  case class Email(sourceEmail: String, title: String, body: String) extends Notification
  case class SMS(sourceNumber: String, message: String) extends Notification
  case class VoiceRecording(contactName: String, link: String) extends Notification
  
  //Instantiating a case class is easy: (Note that we don’t need to use the new keyword)
  val emailFromJohn = Email("john.doe@mail.com", "Greetings From John!", "Hello World!")
  
  //The constructor parameters of case classes are treated as public values and can be accessed directly
  val title = emailFromJohn.title
  println(title) // prints "Greetings From John!"
  
  val editedEmail = emailFromJohn.copy(title = "I am learning Scala!", body = "It's so cool!")
  println(emailFromJohn) // prints "Email(john.doe@mail.com,Greetings From John!,Hello World!)"
  println(editedEmail) // prints "Email(john.doe@mail.com,I am learning Scala,It's so cool!)"
  
  //The following will print "They are equal" and SMS is: SMS(12345, Hello!)
  val firstSms = SMS("12345", "Hello!")
  val secondSms = SMS("12345", "Hello!")
  if (firstSms == secondSms) {
    println("They are equal!")
  }
  println("SMS is: " + firstSms)
  
  def showNotification(notification: Notification): String = {
    notification match {
      case Email(email, title, _) =>
        "You got an email from " + email + " with title: " + title
      case SMS(number, message) =>
        "You got an SMS from " + number + "! Message: " + message
      case VoiceRecording(name, link) =>
        "you received a Voice Recording from " + name + "! Click the link to hear it: " + link
    }
  }
  
  val someSms = SMS("12345", "Are you there?")
  val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")
  println(showNotification(someSms))
  println(showNotification(someVoiceRecording))
  // prints:
  // You got an SMS from 12345! Message: Are you there?
  // you received a Voice Recording from Tom! Click the link to hear it: voicerecording.org/id/123
}