package it.restapp.project.config;

import it.restapp.project.dtos.AuthorDto;
import it.restapp.project.dtos.BookDto;
import it.restapp.project.dtos.BookPriceDto;
import it.restapp.project.dtos.ReviewDto;
import it.restapp.project.entities.Author;
import it.restapp.project.entities.Book;
import it.restapp.project.entities.BookPrice;
import it.restapp.project.entities.Review;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(authorMapping);
        modelMapper.addMappings(authorDtoMapping);
        modelMapper.addMappings(bookMapping);
        modelMapper.addMappings(bookPrice);
        modelMapper.addMappings(review);
        modelMapper.addConverter(Converter);
        return modelMapper;
    }

    PropertyMap<Author, AuthorDto> authorMapping = new PropertyMap<>()
    {
        protected void configure()
        {
            map().setId(source.getId());
            map().setName(source.getName());
            map().setSurname(source.getSurname());
            map().setDateOfBirth(source.getDateOfBirth());
            map().setNationality(source.getNationality());
        }
    };
    PropertyMap<AuthorDto, Author> authorDtoMapping = new PropertyMap<>()
    {
        protected void configure()
        {
            map().setId(source.getId());
            map().setName(source.getName());
            map().setSurname(source.getSurname());
            map().setDateOfBirth(source.getDateOfBirth());
            map().setNationality(source.getNationality());
        }
    };

    PropertyMap<Book, BookDto> bookMapping = new PropertyMap<>()
    {
        protected void configure()
        {
            map().setIsbn(source.getIsbn());
            map().setTitle(source.getTitle());
            map().setPublisher(source.getBookData().getPublisher());
            map().setBookClassification(source.getBookData().getBookClassification());
            map().setTotalPage(source.getTotalPage());
            map().setTotalSold(source.getBookData().getTotalSold());
        }
    };

    PropertyMap<BookPrice, BookPriceDto> bookPrice = new PropertyMap<>()
    {
        protected void configure()
        {
            map().setPrice(source.getPrice());
            map().setDescription(source.getDescription());
            map().setPriceStartDate(source.getPriceStartDate());
            map().setPriceEndDate(source.getPriceEndDate());
        }
    };
    PropertyMap<Review, ReviewDto> review = new PropertyMap<>() {
        protected void configure() {
            map().setDescription(source.getDescription());
            map().setDate(source.getDate());
            map().setVote(source.getVote());
        }
    };

    org.modelmapper.Converter<String, String> Converter = context -> context.getSource() == null ? "" : context.getSource().trim();
}
