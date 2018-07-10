package de.agiehl.bggCollectionGalleryGenerator;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.agiehl.bggCollectionGalleryGenerator.model.collection.CollectionItem;
import de.agiehl.bggCollectionGalleryGenerator.model.collection.UserCollection;

public class TestJacksonXmlToObject {

	@Test
	public void testCollectionItem() throws Exception {
		String xml = "    <item objecttype=\"thing\" objectid=\"207830\" subtype=\"boardgame\" collid=\"43949096\">\r\n"
				+ " <name sortindex=\"1\">5-Minute Dungeon</name>\r\n" + " <yearpublished>2017</yearpublished>\r\n"
				+ " <status own=\"1\" prevowned=\"0\" fortrade=\"0\" want=\"0\" wanttoplay=\"0\" wanttobuy=\"0\" wishlist=\"0\" preordered=\"0\" lastmodified=\"2017-11-25 02:40:08\" />\r\n"
				+ " <numplays>17</numplays>\r\n" //
				+ "    </item>"; //

		// JacksonXmlModule module = new JacksonXmlModule();
		// module.setDefaultUseWrapper(false);

		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
		xmlMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		CollectionItem userCollection = xmlMapper.readValue(xml, CollectionItem.class);

		assertThat(userCollection.getName()).isEqualTo("5-Minute Dungeon");
		assertThat(userCollection.getNumPlays()).isEqualTo(17);
	}

	@Test
	public void testUserCollection() throws Exception {
		String xml = "<items totalitems=\"225\"  termsofuse=\"https://boardgamegeek.com/xmlapi/termsofuse\" pubdate=\"Tue, 10 Jul 2018 10:19:45 +0000\">\r\n"
				+ "    <item objecttype=\"thing\" objectid=\"207830\" subtype=\"boardgame\" collid=\"43949096\">\r\n"
				+ " <name sortindex=\"1\">5-Minute Dungeon</name>\r\n" + " <yearpublished>2017</yearpublished>\r\n"
				// + "
				// <image>https://cf.geekdo-images.com/original/img/44rTOeM8WiW-rOnrFdeKXdD_4Lg=/0x0/pic3213622.png</image>\r\n"
				// + "
				// <thumbnail>https://cf.geekdo-images.com/thumb/img/k9NMU_P4fgPgdmmyn3R3WHOsirg=/fit-in/200x150/pic3213622.png</thumbnail>\r\n"
				+ " <status own=\"1\" prevowned=\"0\" fortrade=\"0\" want=\"0\" wanttoplay=\"0\" wanttobuy=\"0\" wishlist=\"0\" preordered=\"0\" lastmodified=\"2017-11-25 02:40:08\" />\r\n"
				+ " <numplays>17</numplays>\r\n" //
				+ "    </item>" //
				+ "</items>";

		// JacksonXmlModule module = new JacksonXmlModule();
		// module.setDefaultUseWrapper(false);

		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
		xmlMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		UserCollection userCollection = xmlMapper.readValue(xml, UserCollection.class);

		assertThat(userCollection.getItems()).hasSize(1);
		assertThat(userCollection.getTotalItems()).isGreaterThan(0);
		assertThat(userCollection.getTermsOfUseUrl()).isNotNull();

	}

}
