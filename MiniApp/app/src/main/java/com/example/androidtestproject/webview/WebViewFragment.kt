package com.example.androidtestproject.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {

    private var webview: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webview = WebView(this.activity)

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        params.leftMargin = 0
        params.rightMargin = 0
        params.topMargin = 0
        params.bottomMargin = 0
        webview?.layoutParams = params
        webview?.setPadding(0, 0, 0, 0)

        layoutWebview.addView(webview)
        webview?.webViewClient = WebClient()
        webview?.webChromeClient = WebChromeClients()
        webview?.isHorizontalScrollBarEnabled = false
        webview?.isVerticalScrollBarEnabled = true
        webview?.settings?.javaScriptEnabled = true
        webview?.settings?.javaScriptCanOpenWindowsAutomatically = true
//        webview?.settings?.setSupportMultipleWindows(true)
        webview?.settings?.useWideViewPort = true
        webview?.setInitialScale(1)
        webview?.settings?.loadWithOverviewMode = true
        webview?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        webview?.settings?.pluginState = WebSettings.PluginState.ON
        webview?.settings?.defaultTextEncodingName = "euc-kr"
        webview?.settings?.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webview?.settings?.loadsImagesAutomatically = true
        webview?.settings?.setSupportZoom(true)
        webview?.settings?.builtInZoomControls = true
        webview?.settings?.displayZoomControls = false
        webview?.settings?.domStorageEnabled = true
        webview?.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webview?.addJavascriptInterface(this.activity, "Native")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview?.settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

//        webview?.loadUrl("https://m.finset.io")
        webview?.loadUrl("https://expats.finset.io")
//        webview?.loadUrl("https://www.google.com")
    }

    private inner class WebChromeClients : WebChromeClient() {

        override fun onProgressChanged(view: WebView, INT_Progress: Int) {
        }

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return true
        }
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)       //ssl 인증서 에러 방지 : 응용프로그램이 직접 url 처리
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()       //ssl 에러나도 계속 진행하도록 처리
        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            super.onReceivedError(view, errorCode, description, failingUrl)

            when (errorCode) {
                ERROR_AUTHENTICATION // 서버에서 사용자 인증 실패
                    , ERROR_BAD_URL // 잘못된 URL
                    , ERROR_CONNECT // 서버로 연결 실패
                    , ERROR_FAILED_SSL_HANDSHAKE // SSL handshake 수행 실패
                    , ERROR_FILE // 일반 파일 오류
                    , ERROR_FILE_NOT_FOUND // 파일을 찾을 수 없습니다
                    , ERROR_HOST_LOOKUP // 서버 또는 프록시 호스트 이름 조회 실패
                    , ERROR_IO // 서버에서 읽거나 서버로 쓰기 실패
                    , ERROR_PROXY_AUTHENTICATION // 프록시에서 사용자 인증 실패
                    , ERROR_REDIRECT_LOOP // 너무 많은 리디렉션
                    , ERROR_TIMEOUT // 연결 시간 초과
                    , ERROR_TOO_MANY_REQUESTS // 페이지 로드중 너무 많은 요청 발생
                    , ERROR_UNKNOWN // 일반 오류
                    , ERROR_UNSUPPORTED_AUTH_SCHEME // 지원되지 않는 인증 체계
                    , ERROR_UNSUPPORTED_SCHEME -> {
                    view.loadUrl("about:blank")
                    view.loadData("<html>" +
                            "<head>" +
                            "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                            "<meta charset='utf-8'>" +
                            "</head>" +
                            "<body>" +
                            "<p style = 'margin-top:50%;width:100%' align = 'center'>네트워크가 불안정하여 서버에 연결할 수 없습니다.</p>" +
                            "</body>" +
                            "</html>", "text/html; charset=utf-8", "utf-8")
                }
            }
        }
    }
}
