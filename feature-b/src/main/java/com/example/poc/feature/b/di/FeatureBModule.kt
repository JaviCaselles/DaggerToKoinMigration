package com.example.poc.feature.b.di

import com.example.poc.data.repository.ProductRepository
import com.example.poc.feature.b.domain.GetProductsUseCase
import com.example.poc.feature.b.ui.ProductListFragment
import dagger.Module
import dagger.Provides
import dagger.Binds
import dagger.multibindings.IntoMap
import dagger.android.ContributesAndroidInjector

@Module(includes = [FeatureBModule.FeatureBDeclarations::class])
class FeatureBModule {

    @Module
    interface FeatureBDeclarations {
        @ContributesAndroidInjector
        fun bindProductListFragment(): ProductListFragment

        @Binds
        @IntoMap
        @com.example.poc.common.di.ViewModelKey(com.example.poc.feature.b.ui.ProductListViewModel::class)
        fun bindProductListViewModel(viewModel: com.example.poc.feature.b.ui.ProductListViewModel): androidx.lifecycle.ViewModel
    }

    @Provides
    fun provideGetProductsUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }
}
