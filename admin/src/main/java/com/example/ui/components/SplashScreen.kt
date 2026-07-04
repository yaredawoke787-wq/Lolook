package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppLanguage
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.PremiumRose
import com.example.ui.theme.PureWhite
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreen(
    language: AppLanguage,
    onFinished: () -> Unit
) {
    val context = LocalContext.current
    
    // Core animation states
    var startAnimations by remember { mutableStateOf(false) }
    
    // 1. Scale animation with a beautiful premium overshoot bouncy spring
    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimations) 1.0f else 0.1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "premiumScale"
    )

    // 2. Rotation entry animation (Winds down from -180 degrees to 0 degrees)
    val introRotation by animateFloatAsState(
        targetValue = if (startAnimations) 0f else -180f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "introRotation"
    )

    // 3. Overall Fade-in animation
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimations) 1f else 0f,
        animationSpec = tween(1400, easing = EaseInOutCubic),
        label = "premiumAlpha"
    )
    
    // 4. Branding text Fade-in animation
    val textAlphaAnim by animateFloatAsState(
        targetValue = if (startAnimations) 1f else 0f,
        animationSpec = tween(1600, delayMillis = 600, easing = EaseOutQuart),
        label = "textAlpha"
    )

    // 5. Subtitle description Fade-in animation
    val subtitleAlphaAnim by animateFloatAsState(
        targetValue = if (startAnimations) 0.9f else 0f,
        animationSpec = tween(1600, delayMillis = 1100, easing = EaseOutQuart),
        label = "subtitleAlpha"
    )

    // 6. Infinite loop transitions for high-fidelity ambient movement
    val infiniteTransition = rememberInfiniteTransition(label = "premiumLoops")

    // Continuous 3D Y-axis elegant tilt loop
    val tiltYAnim by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tiltY"
    )

    // Continuous 3D X-axis elegant tilt loop
    val tiltXAnim by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tiltX"
    )

    // Gentle continuous vertical float (bobbing)
    val bobbingAnim by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "continuousBobbing"
    )

    // Continuous breathing scale pulse
    val breathingAnim by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "continuousBreathing"
    )

    // Background particle rotation
    val particleRotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(45000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleRotation"
    )

    // Gyroscope orbit ring rotation
    val orbitRotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbitRotation"
    )

    // Glowing heartbeat pulse for the background glow
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )

    // Gold specular shimmer light sweeping translation
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = -250f,
        targetValue = 750f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, delayMillis = 500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslation"
    )

    LaunchedEffect(Unit) {
        startAnimations = true
        delay(3500) // Splash runs for ~3.5 seconds
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Pristine white background
        contentAlignment = Alignment.Center
    ) {
        // 1. Premium radial ambient glows to construct 3D lighting depth
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            
            // Draw luxury gold soft radial ambient light centered around the logo
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        LuxuryGold.copy(alpha = 0.16f * glowPulse),
                        LuxuryGold.copy(alpha = 0.04f),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth / 2f, canvasHeight / 2.2f),
                    radius = canvasWidth * 0.95f
                )
            )

            // Draw premium rose accent light at the bottom right corner
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        PremiumRose.copy(alpha = 0.1f),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth * 0.85f, canvasHeight * 0.85f),
                    radius = canvasWidth * 0.75f
                )
            )
        }

        // 2. 3D Gyroscope Orbital Rings Rotating around the Center Logo
        Canvas(
            modifier = Modifier
                .size(380.dp)
                .scale(scaleAnim)
                .alpha(alphaAnim)
                .graphicsLayer {
                    rotationZ = -introRotation / 2f
                }
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            
            // Ring 1: Slanted on 3D X & Y axes with gold-to-rose gradient
            withTransform({
                translate(center.x, center.y)
                rotate(35f) // tilt of the orbital plane
                scale(1.0f, 0.28f) // 3D squish projection
                rotate(orbitRotationAngle) // active rotation
            }) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            LuxuryGold.copy(alpha = 0.05f),
                            LuxuryGold,
                            PremiumRose,
                            LuxuryGold,
                            LuxuryGold.copy(alpha = 0.05f)
                        )
                    ),
                    radius = 320f,
                    style = Stroke(width = 3.dp.toPx())
                )
            }

            // Ring 2: Opposing slant for complex multi-axis 3D appearance
            withTransform({
                translate(center.x, center.y)
                rotate(-45f) // opposing tilt
                scale(1.0f, 0.22f) // different 3D angle squish
                rotate(-orbitRotationAngle * 1.3f) // rotating the other direction
            }) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            PremiumRose.copy(alpha = 0.02f),
                            PremiumRose,
                            LuxuryGold,
                            PremiumRose,
                            PremiumRose.copy(alpha = 0.02f)
                        )
                    ),
                    radius = 270f,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        // 3. Decorative rotating starlight particle Canvas background
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleAnim)
                .alpha(alphaAnim)
        ) {
            val center = Offset(size.width / 2f, size.height / 2.2f)
            val random = Random(88) // Set deterministic random generator
            
            val numParticles = 45
            for (i in 0 until numParticles) {
                val distance = 160f + random.nextFloat() * 460f
                val sizeVal = 2f + random.nextFloat() * 8f
                val baseAngle = (i * (360f / numParticles)) + (particleRotationAngle * (0.6f + random.nextFloat() * 0.8f))
                val rad = Math.toRadians(baseAngle.toDouble())
                val pX = center.x + (distance * kotlin.math.cos(rad)).toFloat()
                val pY = center.y + (distance * kotlin.math.sin(rad)).toFloat()
                
                // Draw luxury sparkling gold dust beautifully on white background
                drawCircle(
                    color = LuxuryGold.copy(alpha = 0.15f + random.nextFloat() * 0.45f),
                    radius = sizeVal,
                    center = Offset(pX, pY)
                )
            }
        }

        // 4. Central branding and premium logo image
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(310.dp)
                    .scale(scaleAnim)
                    .alpha(alphaAnim),
                contentAlignment = Alignment.Center
            ) {
                // Background ambient soft blur circle behind the asset for luxury volume
                Box(
                    modifier = Modifier
                        .size(230.dp)
                        .blur(45.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    LuxuryGold.copy(alpha = 0.24f),
                                    PremiumRose.copy(alpha = 0.12f)
                                )
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )

                // High fidelity customized premium animated 3D splash image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .offset(y = bobbingAnim.dp)
                        .graphicsLayer {
                            rotationZ = introRotation
                            rotationY = tiltYAnim
                            rotationX = tiltXAnim
                            cameraDistance = 14 * density
                        }
                        .scale(breathingAnim)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_splash_logo),
                        contentDescription = "Teke Man Promotion Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )

                    // specular golden sheen sweeping on top of the logo
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Drawing sweeping reflection light highlight bar
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.0f),
                                    LuxuryGold.copy(alpha = 0.35f),
                                    Color.White.copy(alpha = 0.7f),
                                    LuxuryGold.copy(alpha = 0.35f),
                                    Color.Transparent
                                ),
                                start = Offset(shimmerTranslate, 0f),
                                end = Offset(shimmerTranslate + 160f, size.height)
                            ),
                            alpha = 0.8f
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Sub-greeting Welcome To
            Text(
                text = if (language == AppLanguage.ENGLISH) "WELCOME TO" else "እንኳን ወደ",
                color = LuxuryGold,
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 3.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(textAlphaAnim)
                    .padding(bottom = 6.dp)
            )

            // Teke Promotion Master Branding Title in Slate-Dark for perfect readability
            Text(
                text = "TEKE MAN PROMOTION",
                color = Color(0xFF0F172A), // Premium slate dark
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(textAlphaAnim)
                    .padding(bottom = 12.dp)
            )

            // Dynamic Accent Line with shimmering gradient
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(2.dp)
                    .alpha(textAlphaAnim)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                LuxuryGold,
                                PremiumRose,
                                LuxuryGold,
                                Color.Transparent
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Elegant subtitle description
            Text(
                text = if (language == AppLanguage.ENGLISH) 
                    "THE ULTIMATE GIFT LUXURY" 
                else 
                    "እጅግ የላቀ የስጦታ ልምድ",
                color = Color(0xFF475569), // Slate 600 for outstanding readability
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(subtitleAlphaAnim)
            )
        }
    }
}
