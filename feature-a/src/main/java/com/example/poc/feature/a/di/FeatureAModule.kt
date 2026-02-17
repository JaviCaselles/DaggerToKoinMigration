package com.example.poc.feature.a.di

import com.example.poc.common.di.DI
import com.example.poc.data.repository.UserRepository
import com.example.poc.feature.a.domain.GetUsersUseCase
import com.example.poc.feature.a.ui.UserListFragment
import dagger.Module
import dagger.Provides
import dagger.Binds
import dagger.multibindings.IntoMap
import dagger.android.ContributesAndroidInjector

@Module(includes = [FeatureAModule.FeatureADeclarations::class])
class FeatureAModule {

    @Module
    interface FeatureADeclarations {
        @ContributesAndroidInjector
        fun bindUserListFragment(): UserListFragment
        
        @Binds
        @IntoMap
        @com.example.poc.common.di.ViewModelKey(com.example.poc.feature.a.ui.UserListViewModel::class)
        fun bindUserListViewModel(viewModel: com.example.poc.feature.a.ui.UserListViewModel): androidx.lifecycle.ViewModel
    }

    @Provides
    fun provideGetUsersUseCase(): GetUsersUseCase {
        val userRepository: UserRepository = DI.get()
        return GetUsersUseCase(userRepository)
    }
}

