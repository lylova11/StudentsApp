import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.any
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.stubbing.Scenario

object MockPersonsApi {

    private const val STEP_1 = "step 1"
    private const val STEP_2 = "step 2"

    fun stubGetUsers() {
        stubFor(
            get("/api/")
                .inScenario("My scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(
                            WireMockHelper.getUser(1)
                        )
                ).willSetStateTo(STEP_1)
        )

        stubFor(
            get("/api/")
                .inScenario("My scenario")
                .whenScenarioStateIs(STEP_1)
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(
                            WireMockHelper.getUser(2)
                        )
                ).willSetStateTo(STEP_2)
        )

        stubFor(
            get("/api/")
                .inScenario("My scenario")
                .whenScenarioStateIs(STEP_2)
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(
                            WireMockHelper.getUser(3)
                        )
                )
        )
    }

    val stubNoInternetConnection = stubFor(
        any(urlMatching(".*")).willReturn(
            aResponse()
                .withStatus(503)
                .withBody("")
        )
    )
}