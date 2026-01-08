package furlan.project.transaction.mapper;


import furlan.project.transaction.dto.TransactionRequestDTO;
import furlan.project.transaction.model.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionEntity toEntity(TransactionRequestDTO dto);
}
