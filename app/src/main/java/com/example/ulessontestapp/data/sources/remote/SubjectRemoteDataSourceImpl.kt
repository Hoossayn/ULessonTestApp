package com.example.ulessontestapp.data.sources.remote

import com.example.ulessontestapp.data.model.SubjectResponse
import com.example.ulessontestapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubjectRemoteDataSourceImpl @Inject constructor(
    private val subjectService: SubjectService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubjectRemoteDataSource {
    override suspend fun getSubjectData(): Resource<SubjectResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = subjectService.getSubjectData()
                if (response.isSuccessful) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.errorBody().toString(), null)
                }
            } catch (e: Exception) {
                Resource.error(e.message!!, null)
            } catch (IO: Exception) {
                Resource.error("No internet access", null)
            }
        }
}