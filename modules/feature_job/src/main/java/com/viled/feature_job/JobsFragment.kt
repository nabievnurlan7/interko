package com.viled.feature_job

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Job
import com.viled.feature_job.JobsViewModel.UiState
import com.viled.feature_job.databinding.FragmentJobsBinding
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobsFragment : BaseFragment(R.layout.fragment_jobs) {

    private val viewModel: JobsViewModel by viewModels()
    private val viewBinding: FragmentJobsBinding by viewBinding()
    private lateinit var jobsAdapter: JobsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> showProgress()
                    is UiState.Data -> {
                        jobsAdapter.setItems(it.jobs)
                        closeProgress()
                    }
                    is UiState.Error -> {
                        closeProgress()
                        Toast.makeText(requireContext(), it.errorType.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()

        initJobsRecycler()

        with(viewBinding) {
            filterButton.setOnClickListener {}
            // toolbar.titleTextView.text = getString(R.string.jobs)
        }
    }


    private fun initJobsRecycler() {
        jobsAdapter = JobsAdapter(
            listener = { _, _, item -> openDetails(item) }
        )

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        with(viewBinding) {

            jobsRecyclerView.apply {
                layoutManager = linearLayoutManager
                adapter = jobsAdapter
            }

            jobsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0 && filterButton.visibility == View.VISIBLE) {
                        filterButton.hide();
                    } else if (dy < 0 && filterButton.visibility != View.VISIBLE) {
                        filterButton.show();
                    }
                }
            })
        }
    }

    private fun openDetails(job: Job) {
        (requireActivity() as ToFlowNavigatable).navigateToFlow(
            NavigationFlow.JobDetailFlow(
                bundleOf("job_detail" to job)
            )
        )
    }
}