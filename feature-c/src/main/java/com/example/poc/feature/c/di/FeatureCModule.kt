package com.example.poc.feature.c.di

import androidx.lifecycle.ViewModel
import com.example.poc.common.di.ViewModelKey
import com.example.poc.feature.c.domain.AggregatedUseCase
import com.example.poc.feature.c.ui.AggregatedViewModel
import com.example.poc.feature.a.domain.GetUsersUseCase
import com.example.poc.feature.b.domain.GetProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [FeatureCModule.FeatureCDeclarations::class])
class FeatureCModule {

    @Module
    interface FeatureCDeclarations {


        @Binds
        @IntoMap
        @ViewModelKey(AggregatedViewModel::class)
        fun bindAggregatedViewModel(viewModel: AggregatedViewModel): ViewModel
    }

    // AggregatedUseCase is already annotated with @Inject, so we don't strictly need a Provides method 
    // if dependencies are available. However, GetUsersUseCase and GetProductsUseCase must be available in the graph.
    // Assuming they are provided by their respective modules or available in the app component.
}
