package com.viled.feature_quiz.quiz_subjects

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.INTERVIEW_MODE
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Mode
import com.viled.feature_quiz.R
import com.viled.feature_quiz.databinding.FragmentSubjectsBinding
import com.viled.feature_quiz.quiz_subjects.SubjectsViewModel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubjectsFragment : BaseFragment(R.layout.fragment_subjects) {

    private val viewBinding: FragmentSubjectsBinding by viewBinding()
    private lateinit var detailDialog: BottomDialog
    private val viewModel: SubjectsViewModel by viewModels()
    private lateinit var subjectsAdapter: SubjectsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> showProgress()
                    is UiState.Data -> {
                        closeProgress()
                        subjectsAdapter.setItems(it.subjects)
                    }
                    is UiState.Error -> {
                        closeProgress()
                    }
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()

        initTagsRecycler()

        with(viewBinding) {
            commonToolbar.setTitle(R.string.subjects)

            startButton.setOnClickListener {
                detailDialog = BottomDialog(listener = { startInterview(Mode(quantity = it)) })
                detailDialog.show(childFragmentManager, "")
            }
        }
    }

    private fun initTagsRecycler() {
        subjectsAdapter = SubjectsAdapter(
            listener = { _, _, item ->
                detailDialog = BottomDialog {
                    startInterview(
                        Mode(quantity = it, subjectId = item.id)
                    )
                }
                detailDialog.show(childFragmentManager, "")
            }
        )

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        with(viewBinding) {
            tagsRecyclerView.apply {
                layoutManager = linearLayoutManager
                adapter = subjectsAdapter
            }

            tagsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0 && startButton.visibility == View.VISIBLE) {
                        startButton.hide();
                    } else if (dy < 0 && startButton.visibility != View.VISIBLE) {
                        startButton.show();
                    }
                }
            })
        }
    }

    private fun startInterview(mode: Mode) {
        findNavController().navigate(
            R.id.action_subjectsFragment_to_quizFragment,
            bundleOf(INTERVIEW_MODE to mode)
        )
    }
}