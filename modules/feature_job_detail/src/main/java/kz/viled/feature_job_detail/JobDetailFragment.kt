package kz.viled.feature_job_detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.viled.core.common.SHORT_INTERVIEW
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Job
import com.viled.core.dto.Mode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_job_detail.*

@AndroidEntryPoint
class JobDetailFragment : BaseFragment(R.layout.fragment_job_detail) {

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Job>(JOB_DETAIL)?.let { job = it }
    }

    override fun setUI() {
        super.setUI()

        initMoveBackDispatcher()
//        backImageView.isVisible = true
//        backImageView.setOnClickListener { findNavController().popBackStack() }

        setData(job)
    }

    private fun setData(jobItem: Job) {
//        titleTextView.text = jobItem.positionName
        jobDescriptionTextView.text = jobItem.positionDescription
        salaryTextView.text = jobItem.salary
        locationTextView.text = jobItem.location
        companyNameTextView.text = jobItem.company.name
        establishedTextView.text = jobItem.company.established
        missionTextView.text = jobItem.company.description
        headquarterTextView.text = jobItem.company.headquarterLocation
        employeesTextView.text = jobItem.company.employees

        //initCollegesRecycler()

        val sliderAdapter = SliderAdapter(requireContext())
        imageSlider.sliderAdapter = sliderAdapter
        jobItem.company.slides.forEach { if (it.isNotBlank()) sliderAdapter.addItem(SliderItem(it)) }

        startInterviewButton.setOnClickListener {
            startInterview(Mode(isRealInterview = true, quantity = SHORT_INTERVIEW, subjectId = 0))
        }
    }

    private fun initCollegesRecycler() {
//        val actionsAdapter = ActionsAdapter(
//            listener = { item ->
//            }
//        )
//
//        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        linearLayoutManager.initialPrefetchItemCount = 20
//
//        actionsRecyclerView.apply {
//            layoutManager = linearLayoutManager
//            adapter = actionsAdapter
//        }
//
//        planViewModel.getActions().observe(this, Observer { actionList ->
//            actionsAdapter.setItems(actionList)
//        })
    }

    private fun startInterview(mode: Mode) {
//        findNavController().navigate(
//            R.id.action_global_job_to_interview,
//            bundleOf(InterviewFragment.INTERVIEW_MODE to mode)
//        )
    }

    companion object {
        const val JOB_DETAIL = "job_detail"
    }
}