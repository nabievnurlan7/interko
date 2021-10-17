package kz.viled.feature_job_detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.INTERVIEW_MODE
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Job
import com.viled.core.dto.Mode
import dagger.hilt.android.AndroidEntryPoint
import kz.viled.feature_job_detail.databinding.FragmentJobDetailBinding

@AndroidEntryPoint
class JobDetailFragment : BaseFragment(R.layout.fragment_job_detail) {

    private lateinit var job: Job
    private val viewBinding: FragmentJobDetailBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Job>(JOB_DETAIL)?.let { job = it }
    }

    override fun setUI() {
        super.setUI()
        initMoveBackDispatcher()
        setData(job)
    }

    private fun setData(jobItem: Job) {
        with(viewBinding) {
            commonToolbar.setTitle(jobItem.positionName)
            commonToolbar.setBackClickListener { findNavController().popBackStack() }

            jobDescriptionTextView.text = jobItem.positionDescription
            salaryTextView.text = jobItem.salary
            locationTextView.text = jobItem.location
            companyNameTextView.text = jobItem.company.name
            establishedTextView.text = jobItem.company.established
            missionTextView.text = jobItem.company.description
            headquarterTextView.text = jobItem.company.headquarterLocation
            employeesTextView.text = jobItem.company.employees

            val sliderAdapter = SliderAdapter(requireContext())
            imageSlider.sliderAdapter = sliderAdapter
            jobItem.company.slides.forEach {
                if (it.isNotBlank()) sliderAdapter.addItem(
                    SliderItem(
                        it
                    )
                )
            }

            startInterviewButton.setOnClickListener {
                startInterview(
                    Mode(
                        isRealInterview = true,
                        quantity = com.viled.core.common.SHORT_INTERVIEW,
                        subjectId = 0
                    )
                )
            }
        }

    }

    private fun startInterview(mode: Mode) {
        findNavController().navigate(
            R.id.action_global_quiz,
            bundleOf(INTERVIEW_MODE to mode)
        )
    }

    companion object {
        const val JOB_DETAIL = "job_detail"
    }
}