package vn.nms.sample.presentation.ui.features.web

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentWebViewBinding
import vn.nms.sample.presentation.ui.base.fragment.BaseFragment

@AndroidEntryPoint
class WebFragment : BaseFragment<FragmentWebViewBinding>() {

    private val args: WebFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title = args.title
        binding.url = args.url
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loading.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loading.visibility = View.VISIBLE
                binding.webView.visibility = View.VISIBLE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                binding.webView.visibility = View.GONE
                super.onReceivedError(view, request, error)
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                if (args.title.isEmpty()) {
                    binding.title = title
                }
            }
        }
        binding.setOnClickBack {
            activity?.onBackPressed()
        }
    }

    override fun getLayoutResource() = R.layout.fragment_web_view
}