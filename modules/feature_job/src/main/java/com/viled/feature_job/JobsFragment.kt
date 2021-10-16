package com.viled.feature_job

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_job.databinding.FragmentJobsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobsFragment : BaseFragment(R.layout.fragment_jobs) {

    private val viewModel: JobsViewModel by viewModels()
    private val viewBinding: FragmentJobsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initJobsRecycler()
        setViews()
    }

    private fun setViews() {
        with(viewBinding) {
            filterButton.setOnClickListener {}
            // toolbar.titleTextView.text = getString(R.string.jobs)
        }
        Log.e("ERROR=", "")

        GlobalScope.async { }
        GlobalScope.launch { }
    }

    private fun initJobsRecycler() {
        val jobsAdapter = JobsAdapter(
            listener = { _, _, item ->
                openDetails(item)
            }
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

        viewModel.getJobs().observe(viewLifecycleOwner, { partnerList ->
            jobsAdapter.setItems(partnerList)
        })
    }

    private fun openDetails(job: Job) {
//        findNavController().navigate(
//            R.id.action_jobsFragment_to_jobDetailFragment,
//            bundleOf(JOB_DETAIL to job)
//        )
    }
}