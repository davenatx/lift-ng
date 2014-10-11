package net.liftmodules.ng.test

trait Client2ServerBindingSpecs extends BaseSpec {
  def tests = {
    "The input text" should "be reflected in the output text" in {
      eventually { id("output").element.text should be ("") }
      textField("input").value = "a"
      eventually { id("output").element.text should be ("a") }
      textField("input").value = "ab"
      eventually { id("output").element.text should be ("ab") }
      textField("input").value = "abc"
      eventually { id("output").element.text should be ("abc") }
    }
  }
}

class Client2ServerSessionBindingSpecs extends Client2ServerBindingSpecs {
  "The Client To Server Session Binding Tests page" should "load" in {
    go to s"$index/client2ServerSessionBind"
    eventually { pageTitle should be ("App: Client 2 Server Session Binding") }
  }

  tests

  "The input" should "persist over a reload of the page" in {
    reloadPage()
    eventually { textField("input").value should be ("abc") }
  }
}

class Client2ServerRequestBindingSpecs extends Client2ServerBindingSpecs {
  "The Client To Server Request Binding Tests page" should "load" in {
    go to s"$index/client2ServerRequestBind"
    eventually { pageTitle should be ("App: Client 2 Server Request Binding") }
  }

  tests

  "The input" should "reset after a reload of the page" in {
    reloadPage()
    eventually {
      id("output").element.text should be ("abc")
      textField("input").value should be ("")
    }
  }
}

class Client2ServerOptimizedBindingSpecs extends Client2ServerBindingSpecs {
  "The Client To Server Optimized Binding Tests page" should "load" in {
    go to s"$index/client2ServerOptimizedBind"
    eventually { pageTitle should be ("App: Client 2 Server Optimized Binding") }
  }

  tests
}
