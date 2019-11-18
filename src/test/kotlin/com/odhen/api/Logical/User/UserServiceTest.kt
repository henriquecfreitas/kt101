//package com.odhen.api.Logical.User
//
//import com.odhen.api.Security.ApiPasswordEncoder
//
//import org.aspectj.lang.annotation.Before
//
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeAll
//
//import org.mockito.Mock
//import org.mockito.Mockito
//
//import org.mockito.ArgumentMatchers.any
//import org.mockito.ArgumentMatchers.anyString
//import org.mockito.MockitoAnnotations.initMocks
//
//
//class UserServiceTest {
//
//    @Mock
//    private val userRepository: UserRepository? = null
//    @Mock
//    private val passwordEncoder = ApiPasswordEncoder()
//
//    private var userService: UserService? = null
//    private var user: User? = null
//
//    @BeforeAll
//    fun setUp() {
//        initMocks(this)
//        userService = UserService(userRepository!!)
//        /*user = User(1, "henrique@email.com", "12345", User.Role.USER)
//
//        Mockito.`when`(userRepository!!.save(any())).thenReturn(user)
//        Mockito.`when`(userRepository!!.findByEmail(anyString())).thenReturn(user)*/
//    }
//
//    @Test
//    fun testFindUserByEmail() {
//        val email = "henrique@email.com"
//        val result = userService!!.findUserByEmail(email)
//        assertEquals(email, result!!.email)
//    }
//
//    @Test
//    fun testSaveUser() {
//        val email = "henrique@email.com"
//        val u = userRepository!!.save(
//                User(null, email, passwordEncoder.encode("12345"), User.Role.USER)
//        )
//        assertEquals(email, u!!.email)
//    }
//}