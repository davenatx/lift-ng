package net.liftmodules.ng
package comet

import Angular.ReturnData

import net.liftweb._
import http._
import common._
import js._
import JE._
import JsCmds._
import scala.xml.NodeSeq

class LiftNgFutureActor extends CometActor {
  def render = NodeSeq.Empty

  def callback(p:Promise) = partialUpdate {
    val js =
      "var es=document.querySelectorAll('"+Angular.appSelectorDefault+"');"+
      "var e,i,l;" +
      "i=0;" +
      "for(l=es.length;i<l;i++){" +
        "e=angular.element(es[i]);" +
        "e.scope().$apply(function(){e.injector().get('plumbing').fulfill("+stringify(p)+")});" +
      "}"
    JsRaw(js)
  }

  def stringify(p:Promise):String = p match {
    case Resolve(id, json) => "{id:\""+id+"\",data:"+json+"}"
    case Reject (id, msg)  => "{id:\""+id+"\",msg:\""+msg+"\"}"
  }

  override def lowPriority = {
    case ReturnData(id, Full(json))         => callback(Resolve(id, json))
    case ReturnData(id, Empty)              => callback(Resolve(id, null))
    case ReturnData(id, Failure(msg, _, _)) => callback(Reject(id, msg))
  }

  sealed trait Promise{def id:String}
  case class Resolve(id:String, json:String) extends Promise
  case class Reject(id:String, msg:String) extends Promise
}
