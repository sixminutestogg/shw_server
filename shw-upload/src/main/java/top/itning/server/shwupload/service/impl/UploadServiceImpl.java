package top.itning.server.shwupload.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwupload.entity.Upload;
import top.itning.server.shwupload.repository.UploadRepository;
import top.itning.server.shwupload.service.UploadService;
import top.itning.server.shwupload.util.ReactiveMongoHelper;

/**
 * @author itning
 * @date 2019/5/2 16:56
 */
@Service
public class UploadServiceImpl implements UploadService {
    private final UploadRepository uploadRepository;
    private final ReactiveMongoHelper reactiveMongoHelper;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository, ReactiveMongoHelper reactiveMongoHelper) {
        this.uploadRepository = uploadRepository;
        this.reactiveMongoHelper = reactiveMongoHelper;
    }

    @Override
    public Mono<Upload> getUploadInfoByWorkId(String studentId, String workId) {
        return uploadRepository.findById(studentId + "|" + workId).switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业上传信息不存在", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<Void> delUploadInfoByWorkId(String studentId, String workId) {
        return uploadRepository.deleteById(studentId + "|" + workId);
    }

    @Override
    public Mono<String> reviewWork(String studentId, String workId) {
        return reactiveMongoHelper.findOneFieldsByQuery(ImmutableMap.of("workId", workId, "studentId", studentId), Upload.class, "review")
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业上传信息不存在", HttpStatus.NOT_FOUND)))
                .map(Upload::getReview);
    }

    @Override
    public Mono<Boolean> existsById(String uploadId) {
        return uploadRepository.existsById(uploadId);
    }

    @Override
    public Mono<Upload> findOneById(String uploadId) {
        return uploadRepository.findById(uploadId);
    }
}
