package vn.nms.sample.data.mapper

import vn.nms.sample.data.entity.MovieEntity
import vn.nms.sample.domain.define.ObjectType
import vn.nms.sample.domain.model.MovieModel
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<MovieEntity, MovieModel> {
    override fun mapFromEntity(type: MovieEntity): MovieModel {
        return MovieModel(
            id = type.getId(),
            name = type.movieName,
            thumb = type.movieThumb,
            description = type.movieDescription
        )
    }

    override fun mapToEntity(type: MovieModel): MovieEntity {
        return MovieEntity(
            objectId = ObjectType.MOVIE.name + "_" + type.id,
            movieName = type.name,
            movieThumb = type.thumb,
            movieDescription = type.description
        )
    }
}