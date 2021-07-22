package com.bpkp.bsmartapp.detail.webview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.databinding.ActivityWebViewPdfBinding
import com.bpkp.bsmartapp.detail.webview.WebViewViewModel


class WebViewPDF : AppCompatActivity() {
    companion object {
        var IDST = "IDST"
    }

    private lateinit var webViewPdfBinding: ActivityWebViewPdfBinding
    private lateinit var webViewViewModel: WebViewViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webViewPdfBinding = ActivityWebViewPdfBinding.inflate(layoutInflater)
        setContentView(webViewPdfBinding.root)
        val idST = intent.getIntExtra(IDST, 0)
//        Log.d("ZZZ",idST.toString())
        webViewViewModel = WebViewViewModel()

        webViewViewModel.getPdf(idST)
        webViewViewModel.getPdf().observe(this, {
            webViewPdfBinding.pdfView.settings.javaScriptEnabled
            webViewPdfBinding.pdfView.webChromeClient
            webViewPdfBinding.pdfView.settings.allowFileAccessFromFileURLs
            webViewPdfBinding.pdfView.settings.allowUniversalAccessFromFileURLs
            webViewPdfBinding.pdfView.webChromeClient
            webViewPdfBinding.pdfView.loadUrl(it.url)

//            webViewPdfBinding.pdfView.loadUrl("https://www.themoviedb.org/")
//            Log.d("ZZZ",it.url)
        })
//        webViewPdfBinding.pdfView.loadUrl("https://youtu.be/nxed_CFWmAo")


//        val webview = webViewPdfBinding.pdfView
////        progressbar = findViewById<View>(R.id.progressbar) as ProgressBar
//        webview.settings.javaScriptEnabled = true
//        val filename = intent.getStringExtra(FILENAME)
//        webview.loadUrl("http://103.226.139.157:8080/api/surattugas/pdf?idst=${IDST}")
//        webview.loadUrl("https://youtu.be/nBxyelpBPAc")

//        webview.setWebViewClient(object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                // do your stuff here
//                progressbar.setVisibility(View.GONE)
//            }
//        })

    }
}