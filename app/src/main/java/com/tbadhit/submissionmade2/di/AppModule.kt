package com.tbadhit.submissionmade2.di

import com.tbadhit.core.domain.usecase.UserInteractor
import com.tbadhit.core.domain.usecase.UserUseCase
import com.tbadhit.submissionmade2.detail.DetailViewModel
import com.tbadhit.submissionmade2.follower.FollowerViewModel
import com.tbadhit.submissionmade2.following.FollowingViewModel
import com.tbadhit.submissionmade2.home.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}