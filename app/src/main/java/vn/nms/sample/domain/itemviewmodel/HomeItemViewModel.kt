package vn.nms.sample.domain.itemviewmodel

import vn.nms.sample.domain.model.AdModel
import vn.nms.sample.domain.model.MovieModel

data class MovieItemViewModel(
    var movie: MovieModel,
    override val idViewModel: String?
) : ItemViewModel

data class AdItemViewModel(
    var ad: AdModel,
    override val idViewModel: String?
) : ItemViewModel

data class EmptyItemViewModel(
    override val idViewModel: String?
) : ItemViewModel