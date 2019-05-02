package top.itning.server.shwupload.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwupload.entity.Upload;

/**
 * @author itning
 * @date 2019/4/29 11:42
 */
public interface UploadRepository extends ReactiveMongoRepository<Upload, String> {
}
