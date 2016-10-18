package com.company.service.load

import java.net.URLEncoder

import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.core.assertion._
import io.gatling.http.Predef._

class ServiceSimulation extends Simulation {
  val httpConf = http.baseURL("http://localhost:8080")
  val postRequests = 42
  val urlParamValue = "value";
  val users = 50
  
  def urlEncode(url: String): String = URLEncoder.encode(url, "UTF-8")
  
  val postEndpoint = repeat(postRequests, "post request") {
      exec(http("post request")
        .post("/post-endpoint")
        .disableUrlEncoding
        .formParam("param", (session: Session) => session.userId.toString)
        .check(status.is(200)))
  }

  val getEndpoint = {
    exec(http("get request")
      .get(s"/$urlParamValue/get-endpoint")
      .disableUrlEncoding
      .check(status.is(200)))
  }

  val scn = scenario("Posts then get")
      .forever {
          exec(postEndpoint)
          .exec(getEndpoint)
      }
  
  setUp(scn.inject(rampUsers(users) over (1 minute)))
      .maxDuration(1 minute)
      .protocols(httpConf)
      .assertions(
          global.failedRequests.count.is(0),
          global.responseTime.mean.lessThan(5000))
}