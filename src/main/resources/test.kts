import com.example.kotlintesting.TestingContext
import org.springframework.context.ApplicationContext
import org.springframework.context.support.FileSystemXmlApplicationContext
import java.util.*
import kotlin.math.sign

println("my args are: ${bindings["arg1"]} and ${bindings["arg2"]}");
/*
val context = bindings["context"]
if (context is ApplicationContext){
    val classContext = context.getBean("testingContext")
    if (classContext is TestingContext){
        classContext.setMessage("this is a test message")
        classContext.getMessage();
    }
}
 */
val props = bindings["props"]
if (props is Properties){
    val property = props.get("avalue")
    if (property is String){
        println(property)
    }
}

